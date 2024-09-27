package com.pokerogue.helper.move.dto;

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
                move.getKoName(),
                type.getName(),
                type.getImage(),
                moveCategory.getEngName(),
                moveCategory.getImage(),
                move.getPower(),
                move.getAccuracy(),
                move.getEffect()
        );
    }
}
