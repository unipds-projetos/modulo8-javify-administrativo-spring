package br.com.unipds.javify.administrativo.repository;

import br.com.unipds.javify.administrativo.domain.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Integer> {
    @Query("SELECT a FROM Assinatura a " +
            "JOIN FETCH a.plano")
    List<Assinatura> buscarAssinaturasComPlano();
}
