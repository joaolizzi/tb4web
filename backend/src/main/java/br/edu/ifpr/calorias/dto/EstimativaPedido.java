package br.edu.ifpr.calorias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EstimativaPedido(
    @NotBlank @Size(max = 300) String descricao
) {}
