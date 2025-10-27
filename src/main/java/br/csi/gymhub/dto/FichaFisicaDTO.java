package br.csi.gymhub.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record FichaFisicaDTO(
        Long id,
        UUID uuid,
        Long aLong, LocalDate dataAvaliacao,
        BigDecimal altura,
        BigDecimal peso,
        BigDecimal biceps,
        BigDecimal antebraco,
        BigDecimal peito,
        BigDecimal cintura,
        BigDecimal quadril,
        BigDecimal perna,
        BigDecimal panturrilha,
        String observacoes
) {}
