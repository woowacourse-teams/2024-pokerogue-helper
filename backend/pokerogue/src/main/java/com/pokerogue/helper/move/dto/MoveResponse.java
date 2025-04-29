package com.pokerogue.helper.move.dto;

import com.pokerogue.helper.global.config.ImageUrl;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.data.MoveCategory;
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

    public static MoveResponse from(Move move) {
        Type type = move.getType();
        MoveCategory moveCategory = move.getMoveCategory();

        return new MoveResponse(
                move.getId(),
                move.getName(),
                type.getName(),
                ImageUrl.getTypeImage(type.getName()),
                moveCategory.getEngName(),
                ImageUrl.getMoveCategoryImage(moveCategory.getEngName()),
                move.getPower(),
                move.getAccuracy(),
                move.getEffect()
        );
    }
}
