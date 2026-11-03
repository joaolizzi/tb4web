package br.edu.ifpr.calorias.service;

import br.edu.ifpr.calorias.dto.UsuarioCadastro;
import br.edu.ifpr.calorias.dto.UsuarioResposta;
import br.edu.ifpr.calorias.model.Usuario;
import br.edu.ifpr.calorias.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Locale;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder senhas;

    public UsuarioService(UsuarioRepository usuarios, PasswordEncoder senhas) {
        this.usuarios = usuarios;
        this.senhas = senhas;
    }

    @Transactional
    public UsuarioResposta cadastrar(UsuarioCadastro dados) {
        String email = dados.email().trim().toLowerCase(Locale.ROOT);
        if (usuarios.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        Usuario usuario = usuarios.save(new Usuario(dados.nome().trim(), email, senhas.encode(dados.senha())));
        return new UsuarioResposta(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    @Transactional(readOnly = true)
    public Long idAutenticado(String email) {
        return usuarios.findByEmailIgnoreCase(email)
            .map(Usuario::getId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }
}
