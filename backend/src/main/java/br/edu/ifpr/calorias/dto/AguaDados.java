package br.edu.ifpr.calorias.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record AguaDados(
    @NotNull @Positive Integer quantidadeMl,
    @NotNull LocalDate data
) {}
