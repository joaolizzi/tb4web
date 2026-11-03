package br.edu.ifpr.calorias.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record RefeicaoDados(
    @NotBlank @Size(max = 150) String nome,
    @NotNull @PositiveOrZero Integer calorias,
    @NotNull LocalDate data
) {}
