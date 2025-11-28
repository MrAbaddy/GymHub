package br.csi.gymhub.service;

import br.csi.gymhub.model.DadosUsuario;
import br.csi.gymhub.model.Usuario;
import br.csi.gymhub.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;

    public DadosUsuario cadastrar(Usuario usuario) {
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));

        if (usuario.getPermissao() == null || usuario.getPermissao().isEmpty()) {
            usuario.setPermissao("ADMIN");
        }

        return new DadosUsuario(repository.save(usuario));
    }

    public DadosUsuario findUsuario(Long id) {
        Usuario usuario = repository.getReferenceById(id);
        return new DadosUsuario(usuario);
    }

    public List<DadosUsuario> findAllUsuarios() {
        return repository.findAll().stream().map(DadosUsuario::new).toList();
    }
}