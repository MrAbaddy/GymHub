package br.csi.gymhub.model.instrutor;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
    Instrutor findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
