package br.csi.gymhub.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "ficha_fisica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ficha física do aluno")
public class FichaFisica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    @NotNull(message = "Aluno é obrigatório")
    private Aluno aluno;

    @NotNull
    private LocalDate dataAvaliacao = LocalDate.now();

    @DecimalMin("0.0")
    private BigDecimal altura;

    @DecimalMin("0.0")
    private BigDecimal peso;

    @DecimalMin("0.0")
    private BigDecimal biceps;

    @DecimalMin("0.0")
    private BigDecimal antebraco;

    @DecimalMin("0.0")
    private BigDecimal peito;

    @DecimalMin("0.0")
    private BigDecimal cintura;

    @DecimalMin("0.0")
    private BigDecimal quadril;

    @DecimalMin("0.0")
    private BigDecimal perna;

    @DecimalMin("0.0")
    private BigDecimal panturrilha;

    private String observacoes;
}
