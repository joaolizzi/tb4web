package br.edu.ifpr.calorias.service;

import br.edu.ifpr.calorias.dto.MetaDados;
import br.edu.ifpr.calorias.model.Meta;
import br.edu.ifpr.calorias.model.Usuario;
import br.edu.ifpr.calorias.repository.MetaRepository;
import br.edu.ifpr.calorias.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class MetaService {
    private final MetaRepository metas;
    private final UsuarioRepository usuarios;

    public MetaService(MetaRepository metas, UsuarioRepository usuarios) {
        this.metas = metas;
        this.usuarios = usuarios;
    }

    @Transactional(readOnly = true)
    public Optional<MetaDados> consultar(Long usuarioId) {
        return metas.findByUsuarioId(usuarioId)
            .map(meta -> new MetaDados(meta.getCaloriasDiarias(), meta.getAguaDiariaMl()));
    }

    @Transactional
    public MetaDados salvar(Long usuarioId, MetaDados dados) {
        if (dados == null || dados.caloriasDiarias() == null || dados.caloriasDiarias() <= 0
                || dados.aguaDiariaMl() == null || dados.aguaDiariaMl() <= 0) {
            throw new IllegalArgumentException("As metas devem ser maiores que zero.");
        }
        Usuario usuario = usuarios.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        Meta meta = metas.findByUsuarioId(usuarioId).orElseGet(
            () -> new Meta(usuario, dados.caloriasDiarias(), dados.aguaDiariaMl()));
        meta.setCaloriasDiarias(dados.caloriasDiarias());
        meta.setAguaDiariaMl(dados.aguaDiariaMl());
        metas.save(meta);
        return new MetaDados(meta.getCaloriasDiarias(), meta.getAguaDiariaMl());
    }
}
