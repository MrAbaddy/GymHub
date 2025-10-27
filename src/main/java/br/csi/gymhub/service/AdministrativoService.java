package br.csi.gymhub.service;

import br.csi.gymhub.dto.AdministrativoDTO;
import br.csi.gymhub.model.Administrativo;
import br.csi.gymhub.model.Aluno;
import br.csi.gymhub.repository.AdministrativoRepository;
import br.csi.gymhub.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministrativoService {

    private final AdministrativoRepository repository;
    private final AlunoRepository alunoRepository;

    public AdministrativoService(AdministrativoRepository repository, AlunoRepository alunoRepository) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
    }

    public AdministrativoDTO criar(AdministrativoDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.alunoId()).orElse(null);

        Administrativo adm = new Administrativo();
        adm.setAluno(aluno);
        adm.setModoPagamento(dto.modoPagamento());
        adm.setDiaPagamento(dto.diaPagamento());
        adm.setProximoPagamento(dto.proximoPagamento());
        adm.setSituacao(dto.situacao());

        return mapToDTO(repository.save(adm));
    }

    public List<AdministrativoDTO> listar() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AdministrativoDTO buscarPorId(Long id) {
        return repository.findById(id).map(this::mapToDTO).orElse(null);
    }

    public AdministrativoDTO atualizar(Long id, AdministrativoDTO dto) {
        return repository.findById(id).map(adm -> {
            Aluno aluno = dto.alunoId() != null ? alunoRepository.findById(dto.alunoId()).orElse(adm.getAluno()) : adm.getAluno();

            adm.setAluno(aluno);
            adm.setModoPagamento(dto.modoPagamento());
            adm.setDiaPagamento(dto.diaPagamento());
            adm.setProximoPagamento(dto.proximoPagamento());
            adm.setSituacao(dto.situacao());

            return mapToDTO(repository.save(adm));
        }).orElse(null);
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private AdministrativoDTO mapToDTO(Administrativo adm) {
        return new AdministrativoDTO(
                adm.getId(),
                adm.getUuid(),
                adm.getAluno() != null ? adm.getAluno().getId() : null,
                adm.getModoPagamento(),
                adm.getDiaPagamento(),
                adm.getProximoPagamento(),
                adm.getSituacao()
        );
    }
}
