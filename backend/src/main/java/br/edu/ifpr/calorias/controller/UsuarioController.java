package br.edu.ifpr.calorias.controller;

import br.edu.ifpr.calorias.dto.UsuarioCadastro;
import br.edu.ifpr.calorias.dto.UsuarioResposta;
import br.edu.ifpr.calorias.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarios;

    public UsuarioController(UsuarioService usuarios) {
        this.usuarios = usuarios;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResposta cadastrar(@Valid @RequestBody UsuarioCadastro dados) {
        return usuarios.cadastrar(dados);
    }
}
