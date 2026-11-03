package br.edu.ifpr.calorias.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "registros_agua", indexes = @Index(name = "idx_agua_usuario_data", columnList = "usuario_id,data"))
public class RegistroAgua {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "quantidade_ml", nullable = false)
    private Integer quantidadeMl;

    @Column(nullable = false)
    private LocalDate data;

    protected RegistroAgua() {}

    public RegistroAgua(Usuario usuario, Integer quantidadeMl, LocalDate data) {
        this.usuario = usuario;
        this.quantidadeMl = quantidadeMl;
        this.data = data;
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Integer getQuantidadeMl() { return quantidadeMl; }
    public LocalDate getData() { return data; }
}
