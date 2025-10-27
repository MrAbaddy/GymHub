package br.csi.gymhub.service;

import br.csi.gymhub.dto.AlunoDTO;
import br.csi.gymhub.dto.FichaFisicaDTO;
import br.csi.gymhub.model.Aluno;
import br.csi.gymhub.model.FichaFisica;
import br.csi.gymhub.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public AlunoDTO criar(AlunoDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setIdade(dto.idade());
        aluno.setSexo(dto.sexo());
        aluno.setTelefone(dto.telefone());

        if (dto.fichaFisica() != null) {
            FichaFisica ficha = mapToEntity(dto.fichaFisica());
            ficha.setAluno(aluno);
            aluno.setFichaFisica(ficha);
        }

        return mapToDTO(repository.save(aluno));
    }

    public List<AlunoDTO> listar() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AlunoDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public AlunoDTO atualizar(Long id, AlunoDTO dto) {
        Optional<Aluno> optionalAluno = repository.findById(id);
        if (optionalAluno.isEmpty()) return null;

        Aluno aluno = optionalAluno.get();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setIdade(dto.idade());
        aluno.setSexo(dto.sexo());
        aluno.setTelefone(dto.telefone());

        if (dto.fichaFisica() != null) {
            FichaFisica ficha = aluno.getFichaFisica();
            if (ficha == null) ficha = new FichaFisica();
            FichaFisica fAtualizada = mapToEntity(dto.fichaFisica());
            fAtualizada.setAluno(aluno);
            aluno.setFichaFisica(fAtualizada);
        }

        return mapToDTO(repository.save(aluno));
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private AlunoDTO mapToDTO(Aluno aluno) {
        FichaFisica ficha = aluno.getFichaFisica();
        FichaFisicaDTO fichaDTO = null;
        if (ficha != null) {
            fichaDTO = new FichaFisicaDTO(
                    ficha.getId(),
                    ficha.getUuid(),
                    ficha.getAluno() != null ? ficha.getAluno().getId() : null,
                    ficha.getDataAvaliacao(),
                    ficha.getAltura(),
                    ficha.getPeso(),
                    ficha.getBiceps(),
                    ficha.getAntebraco(),
                    ficha.getPeito(),
                    ficha.getCintura(),
                    ficha.getQuadril(),
                    ficha.getPerna(),
                    ficha.getPanturrilha(),
                    ficha.getObservacoes()
            );
        }

        return new AlunoDTO(
                aluno.getId(),
                aluno.getUuid(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getIdade(),
                aluno.getSexo(),
                aluno.getTelefone(),
                fichaDTO
        );
    }

    private FichaFisica mapToEntity(FichaFisicaDTO dto) {
        FichaFisica ficha = new FichaFisica();
        ficha.setDataAvaliacao(dto.dataAvaliacao());
        ficha.setAltura(dto.altura());
        ficha.setPeso(dto.peso());
        ficha.setBiceps(dto.biceps());
        ficha.setAntebraco(dto.antebraco());
        ficha.setPeito(dto.peito());
        ficha.setCintura(dto.cintura());
        ficha.setQuadril(dto.quadril());
        ficha.setPerna(dto.perna());
        ficha.setPanturrilha(dto.panturrilha());
        ficha.setObservacoes(dto.observacoes());
        return ficha;
    }
}
