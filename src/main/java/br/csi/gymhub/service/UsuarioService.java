package br.csi.gymhub.service;

import br.csi.gymhub.dto.UsuarioDTO;
import br.csi.gymhub.model.Usuario;
import br.csi.gymhub.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioDTO criar(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setSenha(dto.senha());
        return mapToDTO(repository.save(usuario));
    }

    public List<UsuarioDTO> listar() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public UsuarioDTO buscarPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO).orElse(null);
    }

    public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
        return repository.findById(id).map(usuario -> {
            usuario.setNome(dto.nome());
            usuario.setSenha(dto.senha());
            return mapToDTO(repository.save(usuario));
        }).orElse(null);
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private UsuarioDTO mapToDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUuid(),
                usuario.getNome(),
                usuario.getSenha()
        );
    }
}