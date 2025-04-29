package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.global.config.ImageUrl;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.type.data.Type;

public record PokemonMoveResponse(
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

    public static PokemonMoveResponse from(Move move, Integer level) {
        MoveCategory moveCategory = move.getMoveCategory();
        Type moveType = move.getType();

        return new PokemonMoveResponse(
                move.getIndex(),
                move.getName(),
                level,
                move.getPower(),
                move.getAccuracy(),
                moveType.getName(),
                ImageUrl.getTypeImage(moveType.getName()),
                moveCategory.getName(),
                ImageUrl.getMoveCategoryImage(moveCategory.getEngName())
        );
    }

}
