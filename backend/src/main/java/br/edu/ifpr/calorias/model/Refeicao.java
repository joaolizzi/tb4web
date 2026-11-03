package br.edu.ifpr.calorias.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "refeicoes", indexes = @Index(name = "idx_refeicoes_usuario_data", columnList = "usuario_id,data"))
public class Refeicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false)
    private Integer calorias;

    @Column(nullable = false)
    private LocalDate data;

    protected Refeicao() {}

    public Refeicao(Usuario usuario, String nome, Integer calorias, LocalDate data) {
        this.usuario = usuario;
        this.nome = nome;
        this.calorias = calorias;
        this.data = data;
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public String getNome() { return nome; }
    public Integer getCalorias() { return calorias; }
    public LocalDate getData() { return data; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCalorias(Integer calorias) { this.calorias = calorias; }
    public void setData(LocalDate data) { this.data = data; }
}
