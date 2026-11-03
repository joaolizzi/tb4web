package br.edu.ifpr.calorias.service;

import br.edu.ifpr.calorias.dto.RefeicaoDados;
import br.edu.ifpr.calorias.model.Refeicao;
import br.edu.ifpr.calorias.repository.RefeicaoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class RefeicaoService {
    private final RefeicaoRepository repositorio;

    public RefeicaoService(RefeicaoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Refeicao> listar(LocalDate data) {
        return repositorio.findByDataOrderByIdDesc(data);
    }

    public Refeicao cadastrar(RefeicaoDados dados) {
        return repositorio.save(new Refeicao(dados.nome().trim(), dados.calorias(), dados.data()));
    }

    public boolean excluir(Long id) {
        if (!repositorio.existsById(id)) return false;
        repositorio.deleteById(id);
        return true;
    }

    public long total(LocalDate data) {
        return listar(data).stream().mapToLong(Refeicao::getCalorias).sum();
    }
}
