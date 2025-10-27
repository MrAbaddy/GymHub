package br.csi.gymhub.model.administrativo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AdministrativoRepository extends JpaRepository<Administrativo, Long> {
    Administrativo findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);

    Administrativo findByAlunoId(Long alunoId);
}
