package br.csi.gymhub.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "ficha_exercicios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Ficha de exercícios do aluno")
public class FichaExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    @NotNull(message = "Aluno é obrigatório")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "instrutor_id")
    private Instrutor instrutor;

    @NonNull
    @NotBlank(message = "Nome do exercício é obrigatório")
    @Column(name = "nome_exercicio", nullable = false)
    private String nomeExercicio;

    @Min(value = 0, message = "Número de séries inválido")
    @Column(name = "num_series")
    private Integer numSeries;

    @Min(value = 0, message = "Número de repetições inválido")
    @Column(name = "num_repeticoes")
    private Integer numRepeticoes;
}
