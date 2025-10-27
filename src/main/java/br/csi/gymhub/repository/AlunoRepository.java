package br.csi.gymhub.repository;

import br.csi.gymhub.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Aluno findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);

    Aluno findAlunoByUuid(UUID uuid);
}