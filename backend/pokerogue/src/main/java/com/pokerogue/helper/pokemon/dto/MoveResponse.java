package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.battle.BattleMove;
import com.pokerogue.helper.battle.MoveCategory;
import com.pokerogue.helper.pokemon.data.Type;

public record MoveResponse(
        String id,
        String name,
        Integer level,
        Integer power,
        Integer accuracy,
        String type,
        String typeLogo,
        String category,
        String categoryLogo
) {

    public static MoveResponse from(BattleMove battleMove, Integer level, String typeImageFromS3) {
        MoveCategory moveCategory = battleMove.category();
        Type firstType = Type.findById(battleMove.type().getName());

        return new MoveResponse(
                battleMove.id(),
                battleMove.name(),
                level,
                battleMove.power(),
                battleMove.accuracy(),
                firstType.getName(),
                typeImageFromS3,
                moveCategory.getName(),
                moveCategory.getImage()
        );
    }

}
