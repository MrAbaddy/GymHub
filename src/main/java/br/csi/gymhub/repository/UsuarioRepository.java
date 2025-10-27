package br.csi.gymhub.repository;

import br.csi.gymhub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNome(String nome);

    Usuario findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
