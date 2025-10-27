package br.csi.gymhub.repository;

import br.csi.gymhub.model.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

    Instrutor findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
