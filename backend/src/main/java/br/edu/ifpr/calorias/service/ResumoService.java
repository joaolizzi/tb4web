package br.edu.ifpr.calorias.service;

import br.edu.ifpr.calorias.dto.ResumoDiario;
import br.edu.ifpr.calorias.model.Meta;
import br.edu.ifpr.calorias.model.Refeicao;
import br.edu.ifpr.calorias.model.RegistroAgua;
import br.edu.ifpr.calorias.repository.MetaRepository;
import br.edu.ifpr.calorias.repository.RefeicaoRepository;
import br.edu.ifpr.calorias.repository.RegistroAguaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
public class ResumoService {
    private final RefeicaoRepository refeicoes;
    private final RegistroAguaRepository agua;
    private final MetaRepository metas;

    public ResumoService(RefeicaoRepository refeicoes, RegistroAguaRepository agua, MetaRepository metas) {
        this.refeicoes = refeicoes;
        this.agua = agua;
        this.metas = metas;
    }

    @Transactional(readOnly = true)
    public ResumoDiario consultar(Long usuarioId, LocalDate data) {
        long calorias = refeicoes.findByUsuarioIdAndDataOrderByIdDesc(usuarioId, data)
            .stream().mapToLong(Refeicao::getCalorias).sum();
        long aguaConsumida = agua.findByUsuarioIdAndDataOrderByIdDesc(usuarioId, data)
            .stream().mapToLong(RegistroAgua::getQuantidadeMl).sum();
        Meta meta = metas.findByUsuarioId(usuarioId).orElse(null);
        return new ResumoDiario(data, calorias,
            meta == null ? null : meta.getCaloriasDiarias(),
            aguaConsumida, meta == null ? null : meta.getAguaDiariaMl());
    }
}
