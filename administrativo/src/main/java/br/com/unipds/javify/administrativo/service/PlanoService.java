package br.com.unipds.javify.administrativo.service;

import br.com.unipds.javify.administrativo.domain.Plano;
import br.com.unipds.javify.administrativo.dto.PlanoRequest;
import br.com.unipds.javify.administrativo.dto.PlanoResponse;
import br.com.unipds.javify.administrativo.repository.PlanoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PlanoService {

    private final PlanoRepository planoRepository;

    public PlanoService(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    public List<PlanoResponse> listarTodos() {
        return planoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public PlanoResponse buscarPorId(Integer id) {
        return planoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado: " + id));
    }

    @Transactional
    public PlanoResponse criar(PlanoRequest request) {
        var plano = new Plano();
        plano.setNome(request.nome());
        plano.setPreco(request.preco());
        plano.setPossuiPropagandas(request.possuiPropagandas());
        plano.setLimiteMembros(request.limiteMembros());
        plano.setModoOffline(request.modoOffline());
        return toResponse(planoRepository.save(plano));
    }

    @Transactional
    public PlanoResponse atualizar(Integer id, PlanoRequest request) {
        var plano = planoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado: " + id));
        plano.setNome(request.nome());
        plano.setPreco(request.preco());
        plano.setPossuiPropagandas(request.possuiPropagandas());
        plano.setLimiteMembros(request.limiteMembros());
        plano.setModoOffline(request.modoOffline());
        return toResponse(planoRepository.save(plano));
    }

    @Transactional
    public void remover(Integer id) {
        if (!planoRepository.existsById(id)) {
            throw new EntityNotFoundException("Plano não encontrado: " + id);
        }
        planoRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void demonstrarRepeatableRead(Integer planoId) throws InterruptedException {
        var primeiroPreco = planoRepository.buscarPrecoPorId(planoId);

        System.out.println("Primeira leitura: " + primeiroPreco);
        System.out.println("Altere o preço no DBeaver e execute COMMIT...");


        Thread.sleep(15_000);

        var segundoPreco = planoRepository.buscarPrecoPorId(planoId);

        System.out.println("Segunda leitura: " + segundoPreco);
    }


    private PlanoResponse toResponse(Plano plano) {
        return new PlanoResponse(
                plano.getId(),
                plano.getNome(),
                plano.getPreco(),
                plano.isPossuiPropagandas(),
                plano.getLimiteMembros(),
                plano.isModoOffline()
        );
    }
}
