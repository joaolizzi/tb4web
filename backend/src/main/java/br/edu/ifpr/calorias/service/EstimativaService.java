package br.edu.ifpr.calorias.service;

import br.edu.ifpr.calorias.dto.EstimativaResposta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.List;

@Service
public class EstimativaService {
    private final ObjectMapper json;
    private final HttpClient cliente = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(8)).build();
    private final String chave;
    private final String modelo;

    public EstimativaService(ObjectMapper json,
        @Value("${GEMINI_API_KEY:}") String chave,
        @Value("${GEMINI_MODEL:gemini-3.7-flash}") String modelo) {
        this.json = json;
        this.chave = chave;
        this.modelo = modelo;
    }

    public EstimativaResposta estimar(String descricao) {
        if (chave.isBlank()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                "Configure GEMINI_API_KEY no backend para usar a IA.");
        }

        String pedido = "Estime o total de calorias em kcal da refeição descrita a seguir. "
            + "Considere quantidades e preparo, e faça uma estimativa razoável quando faltarem informações. "
            + "Responda apenas com o número inteiro de calorias, sem unidades nem explicações. "
            + "Descrição: " + descricao;

        try {
            String corpo = json.writeValueAsString(Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", pedido)))),
                "generationConfig", Map.of("responseMimeType", "text/plain")
            ));

            HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/"
                    + modelo + ":generateContent"))
                .header("Content-Type", "application/json")
                .header("x-goog-api-key", chave)
                .timeout(Duration.ofSeconds(35))
                .POST(HttpRequest.BodyPublishers.ofString(corpo))
                .build();

            HttpResponse<String> resposta = cliente.send(requisicao,
                HttpResponse.BodyHandlers.ofString());

            if (resposta.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "O serviço de IA não respondeu. Confira a chave, o modelo e os limites da API.");
            }

            JsonNode partes = json.readTree(resposta.body())
                .path("candidates").path(0).path("content").path("parts");
            String texto = partes.path(0).path("text").asText("").trim();

            if (!texto.matches("[0-9]{1,6}")) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "A IA não conseguiu calcular um valor válido. Tente detalhar a refeição.");
            }
            return new EstimativaResposta(Integer.parseInt(texto));
        } catch (ResponseStatusException erro) {
            throw erro;
        } catch (InterruptedException erro) {
            Thread.currentThread().interrupt();
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "A consulta à IA foi interrompida.");
        } catch (Exception erro) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "Não foi possível consultar a IA agora.");
        }
    }
}
