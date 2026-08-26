package br.com.unipds.javify.administrativo.service;

import br.com.unipds.javify.administrativo.domain.LogPagamento;
import br.com.unipds.javify.administrativo.repository.LogPagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@Service
public class LogPagamentoService {

    private final LogPagamentoRepository repository;

    public LogPagamentoService(LogPagamentoRepository repository) {
        this.repository = repository;
    }

   @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarFalha(
            Integer assinaturaId,
            String motivo
    ) {
        var log = new LogPagamento();

        log.setAssinaturaId(assinaturaId);
        log.setMotivo(motivo);
        log.setOcorridoEm(LocalDateTime.now());

        repository.save(log);

        System.out.println(
                "Log de falha gravado para assinatura "
                        + assinaturaId
        );
    }

}
