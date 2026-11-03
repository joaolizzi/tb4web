package br.edu.ifpr.calorias.controller;

import br.edu.ifpr.calorias.dto.AguaDados;
import br.edu.ifpr.calorias.dto.AguaResposta;
import br.edu.ifpr.calorias.service.AguaService;
import br.edu.ifpr.calorias.service.UsuarioService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agua")
public class AguaController {
    private final AguaService agua;
    private final UsuarioService usuarios;

    public AguaController(AguaService agua, UsuarioService usuarios) {
        this.agua = agua;
        this.usuarios = usuarios;
    }

    @GetMapping
    public List<AguaResposta> listar(Principal principal,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return agua.listar(usuarios.idAutenticado(principal.getName()), data)
            .stream().map(AguaResposta::de).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AguaResposta registrar(Principal principal, @Valid @RequestBody AguaDados dados) {
        return AguaResposta.de(agua.registrar(usuarios.idAutenticado(principal.getName()), dados));
    }
}
