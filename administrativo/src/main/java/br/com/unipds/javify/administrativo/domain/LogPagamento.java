package br.com.unipds.javify.administrativo.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LogPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "assinatura_id", nullable = false)
    private Integer assinaturaId;

    @Column(nullable = false)
    private String motivo;

    @Column(name = "ocorrido_em", nullable = false)
    private LocalDateTime ocorridoEm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssinaturaId() {
        return assinaturaId;
    }

    public void setAssinaturaId(Integer assinaturaId) {
        this.assinaturaId = assinaturaId;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getOcorridoEm() {
        return ocorridoEm;
    }

    public void setOcorridoEm(LocalDateTime ocorridoEm) {
        this.ocorridoEm = ocorridoEm;
    }
}
