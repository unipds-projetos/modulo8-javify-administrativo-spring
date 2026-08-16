package br.com.unipds.javify.administrativo.domain;

public class Endereco {

    private String codigoPostal;
    private String logradouro;
    private String bairro;

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @Override
    public String toString() {
        return "Endereco{cep='%s', logradouro='%s', bairro='%s'}".formatted(codigoPostal, logradouro, bairro);
      
    }
}
