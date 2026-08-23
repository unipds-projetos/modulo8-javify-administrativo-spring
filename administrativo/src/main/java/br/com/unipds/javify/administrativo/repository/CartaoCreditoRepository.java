package br.com.unipds.javify.administrativo.repository;

import br.com.unipds.javify.administrativo.domain.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Integer> {

    List<CartaoCredito> findByAssinaturaId(Integer assinaturaId);
}
