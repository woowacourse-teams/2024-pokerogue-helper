package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.battle.BattleMove;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.pokemon.data.LevelMove;
import com.pokerogue.helper.type.data.Type;

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

    public static MoveResponse from(LevelMove levelMove, BattleMove battleMove) {
        MoveCategory moveCategory = battleMove.category();
        Type type = Type.findByEngName(battleMove.type().getName())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_NOT_FOUND));

        return new MoveResponse(
                battleMove.id(),
                battleMove.name(),
                levelMove.getLevel(),
                battleMove.power(),
                battleMove.accuracy(),
                type.getName(), // TODO: API : koName or engName?
                type.getImage(),
                moveCategory.getName(),
                moveCategory.getImage()
        );
    }

}
