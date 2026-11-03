package br.edu.ifpr.calorias.service;

import br.edu.ifpr.calorias.dto.AguaDados;
import br.edu.ifpr.calorias.model.RegistroAgua;
import br.edu.ifpr.calorias.model.Usuario;
import br.edu.ifpr.calorias.repository.RegistroAguaRepository;
import br.edu.ifpr.calorias.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class AguaService {
    private final RegistroAguaRepository registros;
    private final UsuarioRepository usuarios;

    public AguaService(RegistroAguaRepository registros, UsuarioRepository usuarios) {
        this.registros = registros;
        this.usuarios = usuarios;
    }

    @Transactional(readOnly = true)
    public List<RegistroAgua> listar(Long usuarioId, LocalDate data) {
        return registros.findByUsuarioIdAndDataOrderByIdDesc(usuarioId, data);
    }

    @Transactional
    public RegistroAgua registrar(Long usuarioId, AguaDados dados) {
        if (dados == null || dados.quantidadeMl() == null
                || dados.quantidadeMl() <= 0 || dados.data() == null) {
            throw new IllegalArgumentException("Informe uma quantidade de água maior que zero e a data.");
        }
        Usuario usuario = usuarios.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        return registros.save(new RegistroAgua(usuario, dados.quantidadeMl(), dados.data()));
    }
}
