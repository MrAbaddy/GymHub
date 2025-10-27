package br.csi.gymhub.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AdministrativoDTO(
        Long id,
        UUID uuid,
        Long alunoId,
        String modoPagamento,
        Integer diaPagamento,
        LocalDate proximoPagamento,
        String situacao
) {}
