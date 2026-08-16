package br.com.unipds.javify.administrativo.domain;

import java.time.LocalDate;


public class CartaoCredito {

    private Integer id;
    private Assinatura assinatura;
    private String nomeTitular;
    private String ultimosQuatroDigitos;
    private String tokenGateway;
    private LocalDate validade;

    public boolean estaVencido() {
        return validade != null && validade.isBefore(LocalDate.now());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Assinatura getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(Assinatura assinatura) {
        this.assinatura = assinatura;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getUltimosQuatroDigitos() {
        return ultimosQuatroDigitos;
    }

    public void setUltimosQuatroDigitos(String ultimosQuatroDigitos) {
        this.ultimosQuatroDigitos = ultimosQuatroDigitos;
    }

    public String getTokenGateway() {
        return tokenGateway;
    }

    public void setTokenGateway(String tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "CartaoCredito{id=%d, titular='%s', final=%s, validade=%s}"
                .formatted(id, nomeTitular, ultimosQuatroDigitos, validade);
    }
}
