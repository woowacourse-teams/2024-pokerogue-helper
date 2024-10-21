package com.pokerogue.helper.battle;

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
