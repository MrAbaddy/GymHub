package br.csi.gymhub.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "administrativo")
@Getter
@Setter
@NoArgsConstructor // construtor sem argumentos para JPA
@AllArgsConstructor // construtor com todos os campos
@Schema(description = "Controle administrativo do aluno")
public class Administrativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do registro administrativo", example = "1")
    private Long id;

    @UuidGenerator
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    @NotNull(message = "Aluno é obrigatório")
    private Aluno aluno;

    @Size(max = 50)
    private String modoPagamento;

    @Min(1)
    @Max(31)
    private Integer diaPagamento;

    private LocalDate proximoPagamento;

    @Size(max = 20)
    private String situacao;
}