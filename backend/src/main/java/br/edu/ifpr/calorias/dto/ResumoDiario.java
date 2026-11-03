package br.edu.ifpr.calorias.dto;

import java.time.LocalDate;

public record ResumoDiario(
    LocalDate data,
    long caloriasConsumidas,
    Integer metaCalorias,
    long aguaConsumidaMl,
    Integer metaAguaMl
) {}
