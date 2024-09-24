package com.pokerogue.helper.battle.dto;

import com.pokerogue.helper.battle.data.BattleMove;
import com.pokerogue.helper.battle.data.MoveCategory;
import com.pokerogue.helper.type.data.Type;

public record MoveResponse(
        String id,
        String name,
        String typeEngName,
        String typeLogo,
        String categoryEngName,
        String categoryLogo,
        Integer power,
        Integer accuracy,
        String effect
) {

    public static MoveResponse from(BattleMove battleMove) {
        Type type = battleMove.type();
        MoveCategory moveCategory = battleMove.category();

        return new MoveResponse(
                battleMove.id(),
                battleMove.name(),
                type.getName(),
                type.getImage(),
                moveCategory.getEngName(),
                moveCategory.getImage(),
                battleMove.power(),
                battleMove.accuracy(),
                battleMove.effect()
        );
    }
}
