package br.edu.ifpr.calorias.controller;

import br.edu.ifpr.calorias.dto.EstimativaPedido;
import br.edu.ifpr.calorias.dto.EstimativaResposta;
import br.edu.ifpr.calorias.service.EstimativaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ia")
public class EstimativaController {
    private final EstimativaService estimativas;

    public EstimativaController(EstimativaService estimativas) {
        this.estimativas = estimativas;
    }

    @PostMapping("/estimar")
    public EstimativaResposta estimar(@Valid @RequestBody EstimativaPedido pedido) {
        return estimativas.estimar(pedido.descricao());
    }
}
