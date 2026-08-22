package br.com.unipds.javify.administrativo;

import br.com.unipds.javify.administrativo.demonstracao.AcessoJDBC;
import br.com.unipds.javify.administrativo.repository.EnderecoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdministrativoApplication implements CommandLineRunner {

    private final AcessoJDBC acessoJdbc;
    private final EnderecoRepository enderecoRepository;

    public AdministrativoApplication(AcessoJDBC acessoJdbc, EnderecoRepository enderecoRepository) {
        this.acessoJdbc = acessoJdbc;
        this.enderecoRepository = enderecoRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(AdministrativoApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        acessoJdbc.executar();
        System.out.println("Endereço com JPA: " + enderecoRepository.findByCodigoPostal("01508000"));
    }
}
