package br.edu.ifpr.calorias.controller;

import br.edu.ifpr.calorias.dto.ResumoDiario;
import br.edu.ifpr.calorias.service.ResumoService;
import br.edu.ifpr.calorias.service.UsuarioService;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumo")
public class ResumoController {
    private final ResumoService resumos;
    private final UsuarioService usuarios;

    public ResumoController(ResumoService resumos, UsuarioService usuarios) {
        this.resumos = resumos;
        this.usuarios = usuarios;
    }

    @GetMapping
    public ResumoDiario consultar(Principal principal,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return resumos.consultar(usuarios.idAutenticado(principal.getName()), data);
    }
}
