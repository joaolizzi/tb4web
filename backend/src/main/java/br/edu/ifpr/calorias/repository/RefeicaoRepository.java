package br.edu.ifpr.calorias.repository;

import br.edu.ifpr.calorias.model.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    List<Refeicao> findByUsuarioIdAndDataOrderByIdDesc(Long usuarioId, LocalDate data);
    Optional<Refeicao> findByIdAndUsuarioId(Long id, Long usuarioId);
    List<Refeicao> findByUsuarioIdAndDataBetweenOrderByDataAsc(Long usuarioId, LocalDate inicio, LocalDate fim);
}
