package com.pokerogue.helper.battle.dto;

public record BattleResultResponse(
        int power,
        double multiplier,
        double accuracy,
        String moveName,
        String moveDescription,
        String moveType,
        String moveCategory
) {
}
