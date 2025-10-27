package br.csi.gymhub.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa um aluno da academia")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do aluno", example = "1")
    private Long id;

    @UuidGenerator
    private UUID uuid;

    @NonNull
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Min(value = 0, message = "Idade deve ser positiva")
    private Integer idade;

    @Size(max = 10)
    private String sexo;

    @Email(message = "Email inválido")
    private String email;

    @Size(max = 20)
    private String telefone;

    @ManyToMany(mappedBy = "alunos")
    private Set<Instrutor> instrutores = new HashSet<>();

    @OneToOne(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private FichaFisica fichaFisica;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FichaExercicio> fichaExercicios = new HashSet<>();

    @OneToOne(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Administrativo administrativo;

    public void setFichaFisica(FichaFisica ficha) {
        if (ficha != null) {
            ficha.setAluno(this);
        }
        this.fichaFisica = ficha;
    }
}