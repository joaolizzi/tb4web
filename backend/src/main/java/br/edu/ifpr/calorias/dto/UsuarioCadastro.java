package br.edu.ifpr.calorias.dto;

import jakarta.validation.constraints.*;

public record UsuarioCadastro(
    @NotBlank @Size(max = 100) String nome,
    @NotBlank @Email @Size(max = 180) String email,
    @NotBlank @Size(min = 8, max = 72) String senha
) {}
