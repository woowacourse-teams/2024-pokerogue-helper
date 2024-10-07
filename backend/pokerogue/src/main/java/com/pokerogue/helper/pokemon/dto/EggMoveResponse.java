package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.battle.BattleMove;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.type.data.Type;

public record EggMoveResponse(
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

    public static EggMoveResponse from(BattleMove battleMove) {
        MoveCategory moveCategory = battleMove.category();
        Type firstType = Type.findByEngName(battleMove.type().getName())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_MATCHING_ERROR));

        return new EggMoveResponse(
                battleMove.id(),
                battleMove.name(),
                1,
                battleMove.power(),
                battleMove.accuracy(),
                firstType.getName(),
                firstType.getImage(),
                moveCategory.getName(),
                moveCategory.getImage()
        );
    }

}
