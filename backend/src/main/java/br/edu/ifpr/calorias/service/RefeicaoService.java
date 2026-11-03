package br.edu.ifpr.calorias.service;

import br.edu.ifpr.calorias.dto.RefeicaoDados;
import br.edu.ifpr.calorias.exception.RecursoNaoEncontradoException;
import br.edu.ifpr.calorias.model.Refeicao;
import br.edu.ifpr.calorias.model.Usuario;
import br.edu.ifpr.calorias.repository.RefeicaoRepository;
import br.edu.ifpr.calorias.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class RefeicaoService {
    private final RefeicaoRepository refeicoes;
    private final UsuarioRepository usuarios;

    public RefeicaoService(RefeicaoRepository refeicoes, UsuarioRepository usuarios) {
        this.refeicoes = refeicoes;
        this.usuarios = usuarios;
    }

    @Transactional(readOnly = true)
    public List<Refeicao> listar(Long usuarioId, LocalDate data) {
        return refeicoes.findByUsuarioIdAndDataOrderByIdDesc(usuarioId, data);
    }

    @Transactional
    public Refeicao cadastrar(Long usuarioId, RefeicaoDados dados) {
        validar(dados);
        Usuario usuario = usuarios.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        return refeicoes.save(new Refeicao(usuario, dados.nome().trim(), dados.calorias(), dados.data()));
    }

    @Transactional
    public Refeicao editar(Long usuarioId, Long refeicaoId, RefeicaoDados dados) {
        validar(dados);
        Refeicao refeicao = buscarDoUsuario(usuarioId, refeicaoId);
        refeicao.setNome(dados.nome().trim());
        refeicao.setCalorias(dados.calorias());
        refeicao.setData(dados.data());
        return refeicoes.save(refeicao);
    }

    @Transactional
    public void excluir(Long usuarioId, Long refeicaoId) {
        refeicoes.delete(buscarDoUsuario(usuarioId, refeicaoId));
    }

    private Refeicao buscarDoUsuario(Long usuarioId, Long refeicaoId) {
        return refeicoes.findByIdAndUsuarioId(refeicaoId, usuarioId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Refeição não encontrada."));
    }

    private void validar(RefeicaoDados dados) {
        if (dados == null || dados.nome() == null || dados.nome().isBlank()
                || dados.nome().length() > 150 || dados.calorias() == null
                || dados.calorias() < 0 || dados.data() == null) {
            throw new IllegalArgumentException("Informe nome, calorias válidas e data da refeição.");
        }
    }
}
