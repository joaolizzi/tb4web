package br.edu.ifpr.calorias.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "consumos_agua")
public class ConsumoAgua {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantidadeMl;

    @Column(nullable = false)
    private LocalDate data;

    protected ConsumoAgua() {}

    public ConsumoAgua(Integer quantidadeMl, LocalDate data) {
        this.quantidadeMl = quantidadeMl;
        this.data = data;
    }

    public Long getId() { return id; }
    public Integer getQuantidadeMl() { return quantidadeMl; }
    public LocalDate getData() { return data; }
}
