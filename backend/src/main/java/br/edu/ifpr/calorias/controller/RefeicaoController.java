package br.edu.ifpr.calorias.controller;

import br.edu.ifpr.calorias.dto.RefeicaoDados;
import br.edu.ifpr.calorias.dto.HistoricoResposta;
import br.edu.ifpr.calorias.model.Refeicao;
import br.edu.ifpr.calorias.service.RefeicaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/refeicoes")
@CrossOrigin(origins = "http://localhost:5173")
public class RefeicaoController {
    private final RefeicaoService servico;

    public RefeicaoController(RefeicaoService servico) {
        this.servico = servico;
    }

    @GetMapping
    public List<Refeicao> listar(@RequestParam LocalDate data) {
        return servico.listar(data);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Refeicao cadastrar(@Valid @RequestBody RefeicaoDados dados) {
        return servico.cadastrar(dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        return servico.excluir(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/historico")
    public HistoricoResposta historico() {
        return servico.historico();
    }

    @GetMapping("/total")
    public Map<String, Long> total(@RequestParam LocalDate data) {
        return Map.of("total", servico.total(data));
    }
}
