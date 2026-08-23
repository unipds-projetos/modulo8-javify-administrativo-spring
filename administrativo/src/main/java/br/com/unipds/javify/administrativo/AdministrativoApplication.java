package br.com.unipds.javify.administrativo;

import br.com.unipds.javify.administrativo.demonstracao.AcessoJDBC;
import br.com.unipds.javify.administrativo.demonstracao.DemoJpa;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdministrativoApplication implements CommandLineRunner {

    private final AcessoJDBC acessoJdbc;
    private final DemoJpa demoJpa;

    public AdministrativoApplication(AcessoJDBC acessoJdbc, DemoJpa demoJpa) {
        this.acessoJdbc = acessoJdbc;
        this.demoJpa = demoJpa;
    }

    public static void main(String[] args) {
		SpringApplication.run(AdministrativoApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        acessoJdbc.executar();
        demoJpa.buscaEndereco();
       // demoJpa.cadastraEndereco();
        demoJpa.atualizaEndereco();
    }


}
