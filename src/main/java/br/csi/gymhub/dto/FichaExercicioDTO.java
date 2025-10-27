package br.csi.gymhub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record FichaExercicioDTO(
        Long id,
        UUID uuid,
        Long alunoId,
        Long instrutorId,
        @NotBlank(message = "Nome do exercício é obrigatório")
        String nomeExercicio,
        @Min(value = 0, message = "Número de séries inválido")
        Integer numSeries,
        @Min(value = 0, message = "Número de repetições inválido")
        Integer numRepeticoes
) {}
