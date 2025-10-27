package br.csi.gymhub.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record AlunoDTO(
        Long id,
        UUID uuid,
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @Email(message = "Email inválido")
        String email,
        @Min(0)
        Integer idade,
        @Size(max = 10)
        String sexo,
        @Size(max = 20)
        String telefone,
        FichaFisicaDTO fichaFisica // singular
) {}
