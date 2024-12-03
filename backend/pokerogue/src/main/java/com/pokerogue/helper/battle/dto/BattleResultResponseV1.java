package com.pokerogue.helper.battle.dto;

import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.type.data.Type;

public record BattleResultResponseV1(
        int power,
        double multiplier,
        double accuracy,
        String moveName,
        String moveDescription,
        String moveType,
        String moveCategory
) {
    public static BattleResultResponseV1 from(Move move, double multiplier, double accuracy) {
        Type moveType = move.getType();
        MoveCategory moveCategory = move.getMoveCategory();

        return new BattleResultResponseV1(
                move.getPower(),
                multiplier,
                accuracy,
                move.getName(),
                move.getEffect(),
                moveType.getKoName(),
                moveCategory.getName()
        );
    }
}
