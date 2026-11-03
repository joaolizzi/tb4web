package br.edu.ifpr.calorias.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:calorias_test;DB_CLOSE_DELAY=-1",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Transactional
class RefeicaoControllerIntegracaoTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper json;

    private long cadastrar(String nome, int calorias, String data) throws Exception {
        String corpo = json.writeValueAsString(
            new RefeicaoParaTeste(nome, calorias, data)
        );
        String resposta = mvc.perform(post("/api/refeicoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(corpo))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nome").value(nome))
            .andExpect(jsonPath("$.calorias").value(calorias))
            .andReturn().getResponse().getContentAsString();

        JsonNode registro = json.readTree(resposta);
        return registro.path("id").asLong();
    }

    private record RefeicaoParaTeste(String nome, int calorias, String data) {}

    @Test
    void cadastraListaESomaRefeicoes() throws Exception {
        cadastrar("Arroz e feijão", 500, "2026-11-03");
        cadastrar("Banana", 100, "2026-11-03");

        mvc.perform(get("/api/refeicoes").param("data", "2026-11-03"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));

        mvc.perform(get("/api/refeicoes/total").param("data", "2026-11-03"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.total").value(600));
    }

    @Test
    void excluiRefeicaoEAtualizaTotal() throws Exception {
        long id = cadastrar("Almoço", 650, "2026-11-03");

        mvc.perform(delete("/api/refeicoes/" + id))
            .andExpect(status().isNoContent());

        mvc.perform(get("/api/refeicoes/total").param("data", "2026-11-03"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.total").value(0));

        mvc.perform(delete("/api/refeicoes/" + id))
            .andExpect(status().isNotFound());
    }

    @Test
    void calculaHistoricoEMediaSomenteDosDiasCadastrados() throws Exception {
        cadastrar("Café", 300, "2026-11-02");
        cadastrar("Almoço", 700, "2026-11-02");
        cadastrar("Lanche", 500, "2026-11-03");

        mvc.perform(get("/api/refeicoes/historico"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.diasRegistrados").value(2))
            .andExpect(jsonPath("$.mediaCalorias").value(750))
            .andExpect(jsonPath("$.dias.length()").value(2))
            .andExpect(jsonPath("$.dias[0].data").value("2026-11-03"))
            .andExpect(jsonPath("$.dias[1].totalCalorias").value(1000));
    }

    @Test
    void rejeitaRefeicaoSemNome() throws Exception {
        mvc.perform(post("/api/refeicoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"nome":"","calorias":250,"data":"2026-11-03"}
                    """))
            .andExpect(status().isBadRequest());
    }
}
