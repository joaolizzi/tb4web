package br.edu.ifpr.calorias.repository;

import br.edu.ifpr.calorias.model.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    List<Refeicao> findByDataOrderByIdDesc(LocalDate data);
}
