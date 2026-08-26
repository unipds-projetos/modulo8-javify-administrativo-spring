package br.com.unipds.javify.administrativo.repository;

import br.com.unipds.javify.administrativo.domain.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface PlanoRepository extends JpaRepository<Plano, Integer> {

    @Query("SELECT p.preco FROM Plano p WHERE p.id = :id")
    BigDecimal buscarPrecoPorId(@Param("id") Integer id);

}
