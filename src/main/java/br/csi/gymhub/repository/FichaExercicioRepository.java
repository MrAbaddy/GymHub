package br.csi.gymhub.repository;

import br.csi.gymhub.model.FichaExercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FichaExercicioRepository extends JpaRepository<FichaExercicio, Long> {

    FichaExercicio findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
