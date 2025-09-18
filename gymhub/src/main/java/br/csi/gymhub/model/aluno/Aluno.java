package br.csi.gymhub.model.aluno;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "alunos")
@Getter
@Setter

public class Aluno {

    @Id
    private Long id;
}