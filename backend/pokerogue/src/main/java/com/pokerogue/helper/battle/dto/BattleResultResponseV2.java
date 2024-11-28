package com.pokerogue.helper.battle.dto;

import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.type.data.Type;

public record BattleResultResponseV2(
        int power,
        double multiplier,
        double accuracy,
        String moveName,
        String moveDescription,
        String moveType,
        String moveCategory,
        boolean isPreemptive
) {

    public static BattleResultResponseV2 from(Move move, double multiplier, double accuracy, boolean isPreemptive) {
        Type moveType = move.getType();
        MoveCategory moveCategory = move.getMoveCategory();

        return new BattleResultResponseV2(
                move.getPower(),
                multiplier,
                accuracy,
                move.getName(),
                move.getEffect(),
                moveType.getKoName(),
                moveCategory.getName(),
                isPreemptive
        );
    }
}
