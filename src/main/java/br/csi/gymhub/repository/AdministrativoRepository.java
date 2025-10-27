package br.csi.gymhub.repository;

import br.csi.gymhub.model.Administrativo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AdministrativoRepository extends JpaRepository<Administrativo, Long> {

    Administrativo findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
