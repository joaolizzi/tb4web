package br.edu.ifpr.calorias.service;

import br.edu.ifpr.calorias.dto.RefeicaoDados;
import br.edu.ifpr.calorias.dto.HistoricoResposta;
import br.edu.ifpr.calorias.model.Refeicao;
import br.edu.ifpr.calorias.repository.RefeicaoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

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

    // Para o trabalho, o volume de dados é pequeno. Agrupamos em Java para facilitar a explicação.
    public HistoricoResposta historico() {
        var porDia = repositorio.findAll().stream()
            .collect(Collectors.groupingBy(Refeicao::getData));

        List<HistoricoResposta.Dia> dias = porDia.entrySet().stream()
            .map(item -> new HistoricoResposta.Dia(
                item.getKey(),
                item.getValue().stream().mapToLong(Refeicao::getCalorias).sum(),
                item.getValue().size()))
            .sorted(Comparator.comparing(HistoricoResposta.Dia::data).reversed())
            .toList();

        long soma = dias.stream().mapToLong(HistoricoResposta.Dia::totalCalorias).sum();
        long media = dias.isEmpty() ? 0 : Math.round((double) soma / dias.size());

        return new HistoricoResposta(media, dias.size(), dias);
    }

    public long total(LocalDate data) {
        return listar(data).stream().mapToLong(Refeicao::getCalorias).sum();
    }
}
