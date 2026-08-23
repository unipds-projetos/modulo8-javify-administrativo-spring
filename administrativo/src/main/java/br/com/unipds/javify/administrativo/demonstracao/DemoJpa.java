package br.com.unipds.javify.administrativo.demonstracao;

import br.com.unipds.javify.administrativo.domain.Assinatura;
import br.com.unipds.javify.administrativo.domain.Endereco;
import br.com.unipds.javify.administrativo.repository.AssinaturaRepository;
import br.com.unipds.javify.administrativo.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoJpa {

    private final EnderecoRepository enderecoRepository;
    private final AssinaturaRepository assinaturaRepository;

    public DemoJpa(EnderecoRepository enderecoRepository, AssinaturaRepository assinaturaRepository) {
        this.enderecoRepository = enderecoRepository;
        this.assinaturaRepository = assinaturaRepository;
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

    @Transactional
    public void buscarAssinaturaComPlano(){
        List<Assinatura> assinaturas = assinaturaRepository.buscarAssinaturasComPlano();

        for (Assinatura assinatura: assinaturas) {
            System.out.printf("%s -  %s  \n", assinatura.getId(), assinatura.getPlano().getNome() );
        }
    }

    @Transactional
    public void removerCartaoVencido() {
        var assinatura = assinaturaRepository.findById(3);
        System.out.println("Cartões: " + assinatura.get().getCartoes());
        assinatura.get().getCartoes().remove(0);
        System.out.println("Cartões: " + assinatura.get().getCartoes());
    }

}
