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

    public DadosUsuario atualizar(Long id, Usuario dadosAtualizados) {
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setNome(dadosAtualizados.getNome());
        usuarioExistente.setLogin(dadosAtualizados.getLogin());
        usuarioExistente.setPermissao(dadosAtualizados.getPermissao());

        if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isEmpty()) {
            usuarioExistente.setSenha(new BCryptPasswordEncoder().encode(dadosAtualizados.getSenha()));
        }

        return new DadosUsuario(repository.save(usuarioExistente));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public DadosUsuario findUsuario(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new DadosUsuario(usuario);
    }

    public List<DadosUsuario> findAllUsuarios() {
        return repository.findAll().stream().map(DadosUsuario::new).toList();
    }
}