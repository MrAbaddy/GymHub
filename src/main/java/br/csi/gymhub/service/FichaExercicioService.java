package br.csi.gymhub.service;

import br.csi.gymhub.dto.FichaExercicioDTO;
import br.csi.gymhub.model.FichaExercicio;
import br.csi.gymhub.model.Aluno;
import br.csi.gymhub.model.Instrutor;
import br.csi.gymhub.repository.FichaExercicioRepository;
import br.csi.gymhub.repository.AlunoRepository;
import br.csi.gymhub.repository.InstrutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FichaExercicioService {

    private final FichaExercicioRepository repository;
    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;

    public FichaExercicioService(FichaExercicioRepository repository,
                                 AlunoRepository alunoRepository,
                                 InstrutorRepository instrutorRepository) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
        this.instrutorRepository = instrutorRepository;
    }

    public FichaExercicioDTO criar(FichaExercicioDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.alunoId()).orElse(null);
        Instrutor instrutor = dto.instrutorId() != null ?
                instrutorRepository.findById(dto.instrutorId()).orElse(null) : null;

        FichaExercicio ficha = new FichaExercicio();
        ficha.setAluno(aluno);
        ficha.setInstrutor(instrutor);
        ficha.setNomeExercicio(dto.nomeExercicio());
        ficha.setNumSeries(dto.numSeries());
        ficha.setNumRepeticoes(dto.numRepeticoes());

        return mapToDTO(repository.save(ficha));
    }

    public List<FichaExercicioDTO> listar() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public FichaExercicioDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public FichaExercicioDTO atualizar(Long id, FichaExercicioDTO dto) {
        return repository.findById(id).map(ficha -> {
            Aluno aluno = dto.alunoId() != null ? alunoRepository.findById(dto.alunoId()).orElse(ficha.getAluno()) : ficha.getAluno();
            Instrutor instrutor = dto.instrutorId() != null ? instrutorRepository.findById(dto.instrutorId()).orElse(ficha.getInstrutor()) : ficha.getInstrutor();

            ficha.setAluno(aluno);
            ficha.setInstrutor(instrutor);
            ficha.setNomeExercicio(dto.nomeExercicio());
            ficha.setNumSeries(dto.numSeries());
            ficha.setNumRepeticoes(dto.numRepeticoes());

            return mapToDTO(repository.save(ficha));
        }).orElse(null);
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private FichaExercicioDTO mapToDTO(FichaExercicio ficha) {
        return new FichaExercicioDTO(
                ficha.getId(),
                ficha.getUuid(),
                ficha.getAluno() != null ? ficha.getAluno().getId() : null,
                ficha.getInstrutor() != null ? ficha.getInstrutor().getId() : null,
                ficha.getNomeExercicio(),
                ficha.getNumSeries(),
                ficha.getNumRepeticoes()
        );
    }
}
