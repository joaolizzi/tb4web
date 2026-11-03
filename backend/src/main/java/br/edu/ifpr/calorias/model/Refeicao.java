package br.edu.ifpr.calorias.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "refeicoes")
public class Refeicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false)
    private Integer calorias;

    @Column(nullable = false)
    private LocalDate data;

    protected Refeicao() {}

    public Refeicao(String nome, Integer calorias, LocalDate data) {
        this.nome = nome;
        this.calorias = calorias;
        this.data = data;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Integer getCalorias() { return calorias; }
    public LocalDate getData() { return data; }
}
