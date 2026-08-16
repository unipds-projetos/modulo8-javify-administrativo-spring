package br.com.unipds.javify.administrativo.domain;

import java.math.BigDecimal;

public class Plano {

    private Integer id;
    private String nome;
    private BigDecimal preco;
    private boolean possuiPropagandas;
    private int limiteMembros;
    private boolean modoOffline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public boolean isPossuiPropagandas() {
        return possuiPropagandas;
    }

    public void setPossuiPropagandas(boolean possuiPropagandas) {
        this.possuiPropagandas = possuiPropagandas;
    }

    public int getLimiteMembros() {
        return limiteMembros;
    }

    public void setLimiteMembros(int limiteMembros) {
        this.limiteMembros = limiteMembros;
    }

    public boolean isModoOffline() {
        return modoOffline;
    }

    public void setModoOffline(boolean modoOffline) {
        this.modoOffline = modoOffline;
    }

    @Override
    public String toString() {
        return "Plano{id=%d, nome='%s', preco=%s}".formatted(id, nome, preco);
    }
}
