package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.global.config.ImageUrl;
import com.pokerogue.helper.move.data.Move;
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

    public static EggMoveResponse from(Move move) {
        MoveCategory moveCategory = move.getMoveCategory();
        Type type = move.getType();

        return new EggMoveResponse(
                move.getId(),
                move.getName(),
                1,
                move.getPower(),
                move.getAccuracy(),
                type.getName(),
                ImageUrl.getTypeImage(type.getName()),
                moveCategory.getName(),
                ImageUrl.getMoveCategoryImage(moveCategory.getEngName())
        );
    }

}
