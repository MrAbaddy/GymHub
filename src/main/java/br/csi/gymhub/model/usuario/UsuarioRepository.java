package br.csi.gymhub.model.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
