package br.com.unipds.javify.administrativo.demonstracao;

import br.com.unipds.javify.administrativo.domain.Endereco;
import br.com.unipds.javify.administrativo.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class DemoJpa {

    private final EnderecoRepository enderecoRepository;

    public DemoJpa(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public void buscaEndereco(){
        System.out.println("Endereço com JPA: " + enderecoRepository.findByCodigoPostal("01508000"));
    }

    public void cadastraEndereco() {
        var e = new Endereco();
        e.setCodigoPostal("25950000");
        e.setLogradouro("Rua Amaral");
        e.setBairro("Tijuca");
        enderecoRepository.save(e);
    }

    @Transactional
    public void atualizaEndereco() {
        var e = enderecoRepository.findByCodigoPostal("25950000");
        System.out.println("Antes: " + e);
        e.setLogradouro("Rua Não Amaral Não Sincronizada");
        System.out.println("Depois: " + e);
        System.out.println("---");
    }
}
