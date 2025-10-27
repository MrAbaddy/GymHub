package br.csi.gymhub.model.fichaexercicios;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface FichaExerciciosRepository extends JpaRepository<FichaExercicios, Long> {
    FichaExercicios findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);

    List<FichaExercicios> findByAlunoId(Long alunoId);
    List<FichaExercicios> findByInstrutorId(Long instrutorId);
}
