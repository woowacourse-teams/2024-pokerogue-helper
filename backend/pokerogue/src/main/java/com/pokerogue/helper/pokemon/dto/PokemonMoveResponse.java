package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.pokemon.data.Type;

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

    public static PokemonMoveResponse from(Move move, Integer level, String typeImageFromS3) {
        MoveCategory moveCategory = MoveCategory.findByEngName(move.getMoveCategory())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));
        Type firstType = Type.findById(move.getType());

        return new PokemonMoveResponse(
                move.getId(),
                move.getKoName(),
                level,
                move.getPower(),
                move.getAccuracy(),
                firstType.getName(),
                typeImageFromS3,
                moveCategory.getName(),
                moveCategory.getImage()
        );
    }

}
