package br.edu.ifpr.calorias.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record AguaDados(
    @NotNull @Positive Integer quantidadeMl,
    @NotNull LocalDate data
) {}
