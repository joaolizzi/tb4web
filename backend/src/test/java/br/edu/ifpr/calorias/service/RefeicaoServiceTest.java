package br.edu.ifpr.calorias.service;

import br.edu.ifpr.calorias.model.Refeicao;
import br.edu.ifpr.calorias.repository.RefeicaoRepository;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RefeicaoServiceTest {
    @Test
    void calculaHistoricoEMediaConsiderandoApenasDiasComRegistros() {
        RefeicaoRepository repositorio = mock(RefeicaoRepository.class);
        LocalDate ontem = LocalDate.of(2026, 11, 2);
        LocalDate hoje = LocalDate.of(2026, 11, 3);
        when(repositorio.findAll()).thenReturn(List.of(
            new Refeicao("Café", 300, hoje),
            new Refeicao("Almoço", 700, hoje),
            new Refeicao("Jantar", 500, ontem)
        ));

        var historico = new RefeicaoService(repositorio).historico();

        assertEquals(2, historico.diasRegistrados());
        assertEquals(750, historico.mediaCalorias());
        assertEquals(hoje, historico.dias().get(0).data());
        assertEquals(1000, historico.dias().get(0).totalCalorias());
        assertEquals(2, historico.dias().get(0).quantidadeRefeicoes());
        assertEquals(500, historico.dias().get(1).totalCalorias());
    }

    @Test
    void retornaMediaZeroSeNaoExistirRefeicao() {
        RefeicaoRepository repositorio = mock(RefeicaoRepository.class);
        when(repositorio.findAll()).thenReturn(List.of());

        var historico = new RefeicaoService(repositorio).historico();

        assertEquals(0, historico.diasRegistrados());
        assertEquals(0, historico.mediaCalorias());
        assertEquals(0, historico.dias().size());
    }
}
