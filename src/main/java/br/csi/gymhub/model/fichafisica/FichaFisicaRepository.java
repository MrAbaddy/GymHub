package br.csi.gymhub.model.fichafisica;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface FichaFisicaRepository extends JpaRepository<FichaFisica, Long> {
    FichaFisica findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);

    List<FichaFisica> findByAlunoId(Long alunoId);
}
