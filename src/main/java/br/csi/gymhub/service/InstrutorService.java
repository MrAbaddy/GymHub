package br.csi.gymhub.service;

import br.csi.gymhub.dto.InstrutorDTO;
import br.csi.gymhub.model.Instrutor;
import br.csi.gymhub.repository.InstrutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstrutorService {

    private final InstrutorRepository repository;

    public InstrutorService(InstrutorRepository repository) {
        this.repository = repository;
    }

    public InstrutorDTO criar(InstrutorDTO dto) {
        Instrutor instrutor = new Instrutor();
        instrutor.setNome(dto.nome());
        instrutor.setEmail(dto.email());
        instrutor.setTelefone(dto.telefone());
        return mapToDTO(repository.save(instrutor));
    }

    public List<InstrutorDTO> listar() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public InstrutorDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public InstrutorDTO atualizar(Long id, InstrutorDTO dto) {
        return repository.findById(id)
                .map(instrutor -> {
                    instrutor.setNome(dto.nome());
                    instrutor.setEmail(dto.email());
                    instrutor.setTelefone(dto.telefone());
                    return mapToDTO(repository.save(instrutor));
                })
                .orElse(null);
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private InstrutorDTO mapToDTO(Instrutor instrutor) {
        return new InstrutorDTO(
                instrutor.getId(),
                instrutor.getUuid(),
                instrutor.getNome(),
                instrutor.getEmail(),
                instrutor.getTelefone()
        );
    }
}
