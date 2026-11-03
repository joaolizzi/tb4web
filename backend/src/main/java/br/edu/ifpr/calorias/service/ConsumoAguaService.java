package br.edu.ifpr.calorias.service;

import br.edu.ifpr.calorias.dto.AguaDados;
import br.edu.ifpr.calorias.model.ConsumoAgua;
import br.edu.ifpr.calorias.repository.ConsumoAguaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ConsumoAguaService {
    private final ConsumoAguaRepository repositorio;

    public ConsumoAguaService(ConsumoAguaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<ConsumoAgua> listar(LocalDate data) {
        return repositorio.findByDataOrderByIdDesc(data);
    }

    public ConsumoAgua cadastrar(AguaDados dados) {
        return repositorio.save(new ConsumoAgua(dados.quantidadeMl(), dados.data()));
    }

    public boolean excluir(Long id) {
        if (!repositorio.existsById(id)) return false;
        repositorio.deleteById(id);
        return true;
    }

    public long total(LocalDate data) {
        return listar(data).stream().mapToLong(ConsumoAgua::getQuantidadeMl).sum();
    }
}
