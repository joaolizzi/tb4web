package br.edu.ifpr.calorias.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@RestControllerAdvice
public class ErrosEstimativa {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> lidarComErro(ResponseStatusException erro) {
        String mensagem = erro.getReason() == null ? "Não foi possível consultar a IA." : erro.getReason();
        return ResponseEntity.status(erro.getStatusCode()).body(Map.of("erro", mensagem));
    }
}
