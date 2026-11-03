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
class AguaControllerIntegracaoTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper json;

    private long registrarAgua(int quantidadeMl, String data) throws Exception {
        String corpo = json.writeValueAsString(new AguaParaTeste(quantidadeMl, data));
        String resposta = mvc.perform(post("/api/agua")
                .contentType(MediaType.APPLICATION_JSON)
                .content(corpo))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.quantidadeMl").value(quantidadeMl))
            .andReturn().getResponse().getContentAsString();

        JsonNode registro = json.readTree(resposta);
        return registro.path("id").asLong();
    }

    private record AguaParaTeste(int quantidadeMl, String data) {}

    @Test
    void registraListaESomaAguaDoDia() throws Exception {
        registrarAgua(250, "2026-11-03");
        registrarAgua(500, "2026-11-03");

        mvc.perform(get("/api/agua").param("data", "2026-11-03"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));

        mvc.perform(get("/api/agua/total").param("data", "2026-11-03"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.total").value(750));
    }

    @Test
    void excluiRegistroEAtualizaTotal() throws Exception {
        long id = registrarAgua(300, "2026-11-03");

        mvc.perform(delete("/api/agua/" + id))
            .andExpect(status().isNoContent());

        mvc.perform(get("/api/agua/total").param("data", "2026-11-03"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.total").value(0));

        mvc.perform(delete("/api/agua/" + id))
            .andExpect(status().isNotFound());
    }

    @Test
    void rejeitaQuantidadeZero() throws Exception {
        mvc.perform(post("/api/agua")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"quantidadeMl":0,"data":"2026-11-03"}
                    """))
            .andExpect(status().isBadRequest());
    }
}
