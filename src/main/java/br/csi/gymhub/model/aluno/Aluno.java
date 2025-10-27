package br.csi.gymhub.model.aluno;

import br.csi.gymhub.model.administrativo.Administrativo;
import br.csi.gymhub.model.fichaexercicios.FichaExercicios;
import br.csi.gymhub.model.fichafisica.FichaFisica;
import br.csi.gymhub.model.instrutor.Instrutor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
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

    // Relacionamento NxN com Instrutor
    @ManyToMany(mappedBy = "alunos")
    private Set<Instrutor> instrutores;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FichaFisica> fichasFisicas;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FichaExercicios> fichaExercicios;

    @OneToOne(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Administrativo administrativo;
}
