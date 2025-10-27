package br.csi.gymhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record InstrutorDTO(
        Long id,
        UUID uuid,
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @Email(message = "Email inválido")
        String email,
        @Size(min = 10, max = 20, message = "Telefone deve ter entre 10 e 20 caracteres")
        String telefone
) {}
