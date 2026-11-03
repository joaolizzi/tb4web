package br.edu.ifpr.calorias.dto;

import java.time.LocalDate;
import java.util.List;

public record HistoricoResposta(
    long mediaCalorias,
    int diasRegistrados,
    List<Dia> dias
) {
    public record Dia(LocalDate data, long totalCalorias, int quantidadeRefeicoes) {}
}
