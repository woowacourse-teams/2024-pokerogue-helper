package com.pokerogue.helper.move.dto;

import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.type.data.Type;
import java.util.List;

public record MoveDetailResponse(
        String id,
        String name,
        String typeEngName,
        String typeLogo,
        String categoryEngName,
        String categoryLogo,
        Integer power,
        Integer accuracy,
        String effect,
        List<String> pokemonIdsWithLevelMove,
        List<String> pokemonIdsWithEggMove
) {

    public static MoveDetailResponse from(Move move, List<String> levelMoveIdsContains, List<String> eggMoveIdsContains) {
        Type type = move.getType();
        MoveCategory moveCategory = move.getMoveCategory();

        return new MoveDetailResponse(
                move.getId(),
                move.getKoName(),
                type.getName(),
                type.getImage(),
                moveCategory.getEngName(),
                moveCategory.getImage(),
                move.getPower(),
                move.getAccuracy(),
                move.getEffect(),
                levelMoveIdsContains,
                eggMoveIdsContains
        );
    }
}
