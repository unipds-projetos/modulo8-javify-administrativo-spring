package br.com.unipds.javify.administrativo.domain;

import jakarta.persistence.*;

@Entity
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;
    @Column(name = "status_ativa", nullable = false)
    private boolean statusAtiva = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public boolean isStatusAtiva() {
        return statusAtiva;
    }

    public void setStatusAtiva(boolean statusAtiva) {
        this.statusAtiva = statusAtiva;
    }

    @Override
    public String toString() {
        return "Assinatura{id=%d, ativa=%s}".formatted(id, statusAtiva);
    }
}
