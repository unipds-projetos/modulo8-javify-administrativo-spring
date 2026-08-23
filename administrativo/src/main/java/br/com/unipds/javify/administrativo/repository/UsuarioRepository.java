package br.com.unipds.javify.administrativo.repository;

import br.com.unipds.javify.administrativo.domain.Usuario;
import br.com.unipds.javify.administrativo.repository.projection.ResumoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByTitularTrue();

    @Query("SELECT u from Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :termo, '%')) Order by u.nome")
    List<Usuario> buscarPorNome(@Param("termo") String termo);

    @Query(value = "SELECT COUNT(*) FROM usuario WHERE titular = true",nativeQuery = true)
    long contarTitularesAtivos();

    @Query("SELECT u.nome AS nome, u.email AS email FROM Usuario u WHERE u.titular = true ORDER BY u.nome")
    List<ResumoUsuario> listaResumoTitulares();
}
