package br.com.unipds.javify.administrativo.repository;

import br.com.unipds.javify.administrativo.domain.UsuarioTelefone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioTelefoneRepository extends JpaRepository<UsuarioTelefone, Integer> {

    List<UsuarioTelefone> findByUsuarioId(Long usuarioId);
}
