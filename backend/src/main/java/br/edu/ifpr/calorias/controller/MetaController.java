package br.edu.ifpr.calorias.controller;

import br.edu.ifpr.calorias.dto.MetaDados;
import br.edu.ifpr.calorias.service.MetaService;
import br.edu.ifpr.calorias.service.UsuarioService;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metas")
public class MetaController {
    private final MetaService metas;
    private final UsuarioService usuarios;

    public MetaController(MetaService metas, UsuarioService usuarios) {
        this.metas = metas;
        this.usuarios = usuarios;
    }

    @GetMapping
    public ResponseEntity<MetaDados> consultar(Principal principal) {
        return metas.consultar(usuarios.idAutenticado(principal.getName()))
            .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public MetaDados salvar(Principal principal, @Valid @RequestBody MetaDados dados) {
        return metas.salvar(usuarios.idAutenticado(principal.getName()), dados);
    }
}
