package br.com.unipds.javify.administrativo.repository;

import br.com.unipds.javify.administrativo.domain.LogPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogPagamentoRepository extends JpaRepository<LogPagamento, Integer> {
}
