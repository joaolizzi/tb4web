package br.edu.ifpr.calorias.controller;

import br.edu.ifpr.calorias.dto.AguaDados;
import br.edu.ifpr.calorias.model.ConsumoAgua;
import br.edu.ifpr.calorias.service.ConsumoAguaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agua")
@CrossOrigin(origins = "http://localhost:5173")
public class AguaController {
    private final ConsumoAguaService servico;

    public AguaController(ConsumoAguaService servico) {
        this.servico = servico;
    }

    @GetMapping
    public List<ConsumoAgua> listar(@RequestParam LocalDate data) {
        return servico.listar(data);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsumoAgua cadastrar(@Valid @RequestBody AguaDados dados) {
        return servico.cadastrar(dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        return servico.excluir(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/total")
    public Map<String, Long> total(@RequestParam LocalDate data) {
        return Map.of("total", servico.total(data));
    }
}
