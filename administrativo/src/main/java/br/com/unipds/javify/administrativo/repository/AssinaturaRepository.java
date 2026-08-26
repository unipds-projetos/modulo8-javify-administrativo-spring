package br.com.unipds.javify.administrativo.repository;

import br.com.unipds.javify.administrativo.domain.Assinatura;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Integer> {
    @Query("SELECT a FROM Assinatura a " +
            "JOIN FETCH a.plano")
    List<Assinatura> buscarAssinaturasComPlano();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Assinatura a WHERE a.id = :id")
    Optional<Assinatura> findByIdForUpdate(@Param("id") Integer id);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "javax.persistence.lock.timeout", value = "-2"))
    @Query("""
       SELECT a
        FROM Assinatura a
       WHERE a.statusAtiva = true
       ORDER BY a.id
        """)
    List<Assinatura> buscarLoteParaProcessamento(Pageable pageable);

}
