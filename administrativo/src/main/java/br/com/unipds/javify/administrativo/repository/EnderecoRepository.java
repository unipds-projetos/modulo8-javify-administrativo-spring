package br.com.unipds.javify.administrativo.repository;

import br.com.unipds.javify.administrativo.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, String> {
    Endereco findByCodigoPostal(String codigo);
}
