package br.csi.gymhub.repository;

import br.csi.gymhub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findByLogin(String login);
}
