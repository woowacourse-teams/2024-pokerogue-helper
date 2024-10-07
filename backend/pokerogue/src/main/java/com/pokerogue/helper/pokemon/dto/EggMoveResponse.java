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
        Type type = Type.findByEngName(battleMove.type().getName())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_NOT_FOUND));

        return new EggMoveResponse(
                battleMove.id(),
                battleMove.name(),
                1,
                battleMove.power(),
                battleMove.accuracy(),
                type.getName(),
                type.getImage(),
                moveCategory.getName(),
                moveCategory.getImage()
        );
    }

}
