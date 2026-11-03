package br.edu.ifpr.calorias.model;

import jakarta.persistence.*;

@Entity
@Table(name = "metas")
public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "calorias_diarias", nullable = false)
    private Integer caloriasDiarias;

    @Column(name = "agua_diaria_ml", nullable = false)
    private Integer aguaDiariaMl;

    protected Meta() {}

    public Meta(Usuario usuario, Integer caloriasDiarias, Integer aguaDiariaMl) {
        this.usuario = usuario;
        this.caloriasDiarias = caloriasDiarias;
        this.aguaDiariaMl = aguaDiariaMl;
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Integer getCaloriasDiarias() { return caloriasDiarias; }
    public Integer getAguaDiariaMl() { return aguaDiariaMl; }
    public void setCaloriasDiarias(Integer caloriasDiarias) { this.caloriasDiarias = caloriasDiarias; }
    public void setAguaDiariaMl(Integer aguaDiariaMl) { this.aguaDiariaMl = aguaDiariaMl; }
}
