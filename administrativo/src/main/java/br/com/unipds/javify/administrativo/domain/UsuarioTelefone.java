package br.com.unipds.javify.administrativo.domain;

public class UsuarioTelefone {

    private Integer id;
    private Usuario usuario;
    private String numero;
    private TipoTelefone tipo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "UsuarioTelefone{id=%d, numero='%s', tipo=%s}".formatted(id, numero, tipo);
    }
}
