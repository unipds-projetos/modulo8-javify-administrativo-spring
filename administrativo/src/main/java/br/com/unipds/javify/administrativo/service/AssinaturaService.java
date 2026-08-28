package br.com.unipds.javify.administrativo.service;

import br.com.unipds.javify.administrativo.domain.Assinatura;
import br.com.unipds.javify.administrativo.dto.AssinaturaRequest;
import br.com.unipds.javify.administrativo.dto.AssinaturaResponse;
import br.com.unipds.javify.administrativo.dto.CancelamentoDTO;
import br.com.unipds.javify.administrativo.dto.PlanoResponse;
import br.com.unipds.javify.administrativo.repository.AssinaturaRepository;
import br.com.unipds.javify.administrativo.repository.PlanoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final PlanoRepository planoRepository;
    private final LogPagamentoService logPagamentoService;

    public AssinaturaService(AssinaturaRepository assinaturaRepository, PlanoRepository planoRepository, LogPagamentoService logPagamentoService) {
        this.assinaturaRepository = assinaturaRepository;
        this.planoRepository = planoRepository;
        this.logPagamentoService = logPagamentoService;
    }

    public List<AssinaturaResponse> listarTodas() {
        return assinaturaRepository.buscarAssinaturasComPlano().stream()
                .map(this::toResponse)
                .toList();
    }

    public AssinaturaResponse buscarPorId(Integer id) {
        return assinaturaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Assinatura não encontrada: " + id));
    }

    @Transactional
    public AssinaturaResponse criar(AssinaturaRequest request) {
        var plano = planoRepository.findById(request.planoId())
                .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado: " + request.planoId()));
        var assinatura = new Assinatura();
        assinatura.setPlano(plano);
        assinatura.setStatusAtiva(request.statusAtiva());
        return toResponse(assinaturaRepository.save(assinatura));
    }

    @Transactional
    public AssinaturaResponse atualizar(Integer id, AssinaturaRequest request) {
        var assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assinatura não encontrada: " + id));
        var plano = planoRepository.findById(request.planoId())
                .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado: " + request.planoId()));
        assinatura.setPlano(plano);
        assinatura.setStatusAtiva(request.statusAtiva());
        return toResponse(assinaturaRepository.save(assinatura));
    }

    @Transactional
    public void remover(Integer id) {
        if (!assinaturaRepository.existsById(id)) {
            throw new EntityNotFoundException("Assinatura não encontrada: " + id);
        }
        assinaturaRepository.deleteById(id);
    }

    @Transactional
    public void cancelar(Integer id) {
        System.out.println("1 - Entrou no método cancelar: " + id);


        System.out.println("2 - Tentando adquirir o lock...");


        Assinatura assinatura = assinaturaRepository
                .findByIdForUpdate(id)
                .orElseThrow(() -> new EntityNotFoundException("Assinatura não encontrada: " + id));


        System.out.println("3 - Lock adquirido");


        if (!assinatura.isStatusAtiva()) {
            System.out.println("4 - Assinatura já estava cancelada");
            throw new IllegalStateException("Assinatura já está cancelada: " + id);
        }
        System.out.println("5 - Iniciando pausa");


        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação interrompida", e);
        }


        System.out.println("6 - Alterando status");
        assinatura.setStatusAtiva(false);
    }

    @Transactional
    public List<Integer> processarLote(int limite, int segundos
    ) {
        String worker = Thread.currentThread().getName();
        Pageable pageable = PageRequest.of(0, limite);

        System.out.println(worker + " - procurando assinaturas");

        List<Assinatura> assinaturas =
                assinaturaRepository.buscarLoteParaProcessamento(pageable);

        List<Integer> ids = assinaturas.stream()
                .map(Assinatura::getId)
                .toList();

        System.out.println(worker + " - registros bloqueados: " + ids);


        try {
            Thread.sleep(segundos * 1_000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Processamento interrompido", e);
        }

        System.out.println(worker + " - finalizando registros: " + ids);

        return ids;
    }

    @Transactional
    public void alterarStatusComLockOtimista(
            Integer id,
            boolean ativa,
            int segundos
    ) {
        String requisicao = Thread.currentThread().getName();


        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Assinatura não encontrada: " + id
                ));


        System.out.printf(
                "%s leu: id=%d, ativa=%s, versão=%d%n",
                requisicao,
                assinatura.getId(),
                assinatura.isStatusAtiva(),
                assinatura.getVersao()
        );


        try {
            Thread.sleep(segundos * 1_000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operação interrompida", e);
        }

        assinatura.setStatusAtiva(ativa);

        System.out.printf(
                "%s tentando salvar: ativa=%s, versão esperada=%d%n",
                requisicao,
                ativa,
                assinatura.getVersao()
        );

        assinaturaRepository.flush();

        System.out.printf(
                "%s salvou com sucesso; nova versão=%d%n",
                requisicao,
                assinatura.getVersao()
        );
    }

    @Transactional(rollbackFor = {IOException.class})
    public void confirmarPagamento(
            Integer assinaturaId,
            boolean pagamentoAprovado
    ) throws IOException {
        var assinatura = assinaturaRepository
                .findById(assinaturaId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Assinatura não encontrada: " + assinaturaId
                ));

        assinatura.setStatusAtiva(true);

        if (!pagamentoAprovado) {
            logPagamentoService.registrarFalha(
                    assinaturaId,
                    "Pagamento recusado"
            );

            throw new IOException("Pagamento recusado");
        }
    }



    private AssinaturaResponse toResponse(Assinatura assinatura) {
        var plano = assinatura.getPlano();
        var planoResponse = new PlanoResponse(
                plano.getId(),
                plano.getNome(),
                plano.getPreco(),
                plano.isPossuiPropagandas(),
                plano.getLimiteMembros(),
                plano.isModoOffline()
        );
        return new AssinaturaResponse(
                assinatura.getId(),
                planoResponse,
                assinatura.isStatusAtiva()
        );
    }

    @Transactional
    public CancelamentoDTO cancelarComReembolso(Integer assinaturaId) {
        BigDecimal reembolso = assinaturaRepository.calcularReembolso(assinaturaId);


        Assinatura assinatura = assinaturaRepository.findById(assinaturaId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Assinatura não encontrada: " + assinaturaId));
        assinatura.setStatusAtiva(false);

        return new CancelamentoDTO(assinaturaId, reembolso);
    }



}
