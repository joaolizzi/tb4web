package br.edu.ifpr.calorias.repository;

import br.edu.ifpr.calorias.model.ConsumoAgua;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ConsumoAguaRepository extends JpaRepository<ConsumoAgua, Long> {
    List<ConsumoAgua> findByDataOrderByIdDesc(LocalDate data);
}
