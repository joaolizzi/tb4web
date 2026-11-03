package br.edu.ifpr.calorias.dto;

import br.edu.ifpr.calorias.model.RegistroAgua;
import java.time.LocalDate;

public record AguaResposta(Long id, Integer quantidadeMl, LocalDate data) {
    public static AguaResposta de(RegistroAgua registro) {
        return new AguaResposta(registro.getId(), registro.getQuantidadeMl(), registro.getData());
    }
}
