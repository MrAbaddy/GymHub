package br.csi.gymhub.service;

import br.csi.gymhub.model.aluno.Aluno;
import br.csi.gymhub.model.aluno.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {this.repository = repository;}
    public void salvar(Aluno aluno) {repository.save(aluno);}

    public List<Aluno> findAll() {
        return repository.findAll();
    }

}