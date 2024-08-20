package com.pokerogue.helper.battle;

import java.util.List;

public record BattleResultResponse(
        int power,
        double multiplier,
        double accuracy,
        String moveName,
        String moveDescription,
        String moveType,
        String moveCategory,
        String weatherDescription,
        List<String> weatherEffect
) {
}
