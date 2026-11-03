package br.edu.ifpr.calorias.controller;

import br.edu.ifpr.calorias.dto.RefeicaoDados;
import br.edu.ifpr.calorias.dto.RefeicaoResposta;
import br.edu.ifpr.calorias.service.RefeicaoService;
import br.edu.ifpr.calorias.service.UsuarioService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refeicoes")
public class RefeicaoController {
    private final RefeicaoService refeicoes;
    private final UsuarioService usuarios;

    public RefeicaoController(RefeicaoService refeicoes, UsuarioService usuarios) {
        this.refeicoes = refeicoes;
        this.usuarios = usuarios;
    }

    @GetMapping
    public List<RefeicaoResposta> listar(Principal principal,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return refeicoes.listar(usuarios.idAutenticado(principal.getName()), data)
            .stream().map(RefeicaoResposta::de).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RefeicaoResposta cadastrar(Principal principal, @Valid @RequestBody RefeicaoDados dados) {
        return RefeicaoResposta.de(refeicoes.cadastrar(usuarios.idAutenticado(principal.getName()), dados));
    }

    @PutMapping("/{id}")
    public RefeicaoResposta editar(Principal principal, @PathVariable Long id,
        @Valid @RequestBody RefeicaoDados dados) {
        return RefeicaoResposta.de(refeicoes.editar(usuarios.idAutenticado(principal.getName()), id, dados));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(Principal principal, @PathVariable Long id) {
        refeicoes.excluir(usuarios.idAutenticado(principal.getName()), id);
    }
}
