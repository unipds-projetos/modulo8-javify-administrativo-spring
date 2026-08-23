package br.com.unipds.javify.administrativo.demonstracao;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("demo")
public class ExecutaDemo implements CommandLineRunner {

    private final AcessoJDBC acessoJdbc;
    private final DemoJpa demoJpa;
    
    public ExecutaDemo(AcessoJDBC acessoJdbc, DemoJpa demoJpa) {
        this.acessoJdbc = acessoJdbc;
        this.demoJpa = demoJpa;
    }


    @Override
    public void run(String... args) throws Exception {
        acessoJdbc.executar();
        demoJpa.buscaEndereco();
        demoJpa.cadastraEndereco();
        demoJpa.atualizaEndereco();
        demoJpa.buscarAssinaturaComPlano();
        demoJpa.removerCartaoVencido();
        demoJpa.testarConsultas();
    }
    
}
