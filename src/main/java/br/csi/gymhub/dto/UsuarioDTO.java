package br.csi.gymhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record UsuarioDTO(
        Long id,
        UUID uuid,
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha
) {}
