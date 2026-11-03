package br.edu.ifpr.calorias.repository;

import br.edu.ifpr.calorias.model.RegistroAgua;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface RegistroAguaRepository extends JpaRepository<RegistroAgua, Long> {
    List<RegistroAgua> findByUsuarioIdAndDataOrderByIdDesc(Long usuarioId, LocalDate data);
}
