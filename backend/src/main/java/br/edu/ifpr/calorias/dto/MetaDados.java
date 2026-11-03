package br.edu.ifpr.calorias.dto;

import jakarta.validation.constraints.*;

public record MetaDados(
    @NotNull @Positive Integer caloriasDiarias,
    @NotNull @Positive Integer aguaDiariaMl
) {}
