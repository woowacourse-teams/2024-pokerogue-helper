package com.pokerogue.helper.move.dto;

import com.pokerogue.helper.global.config.ImageUrl;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.move.data.MoveFlag;
import com.pokerogue.helper.type.data.Type;
import java.util.List;

public record MoveDetailResponse(
        String id,
        String name,
        String typeEngName,
        String typeLogo,
        String categoryEngName,
        String categoryLogo,
        String moveTarget,
        Integer power,
        Integer accuracy,
        Integer powerPoint,
        String effect,
        Integer effectChance,
        Integer priority,
        Integer generation,
        String released,
        List<String> flags,
        List<String> pokemonIdsWithLevelMove,
        List<String> pokemonIdsWithEggMove
) {

    public static MoveDetailResponse from(Move move, List<String> levelMoveIdsContains, List<String> eggMoveIdsContains) {
        Type type = move.getType();
        MoveCategory moveCategory = move.getMoveCategory();
        List<String> moveFlags = move.getFlags().stream()
                .map(MoveFlag::getId)
                .toList();

        return new MoveDetailResponse(
                move.getId(),
                move.getKoName(),
                type.getName(),
                ImageUrl.getTypeImage(type.getName()),
                moveCategory.getEngName(),
                ImageUrl.getMoveCategoryImage(moveCategory.getEngName()),
                move.getMoveTarget().getId(),
                move.getPower(),
                move.getAccuracy(),
                move.getPowerPoint(),
                move.getEffect(),
                move.getEffectChance(),
                move.getPriority(),
                move.getGeneration(),
                move.getReleased(),
                moveFlags,
                levelMoveIdsContains,
                eggMoveIdsContains
        );
    }
}
