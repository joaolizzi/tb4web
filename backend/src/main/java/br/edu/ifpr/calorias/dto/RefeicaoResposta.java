package br.edu.ifpr.calorias.dto;

import br.edu.ifpr.calorias.model.Refeicao;
import java.time.LocalDate;

public record RefeicaoResposta(Long id, String nome, Integer calorias, LocalDate data) {
    public static RefeicaoResposta de(Refeicao refeicao) {
        return new RefeicaoResposta(refeicao.getId(), refeicao.getNome(), refeicao.getCalorias(), refeicao.getData());
    }
}
