package com.pokerogue.helper.pokemon2.dto;

import com.pokerogue.helper.pokemon2.data.Move;
import com.pokerogue.helper.pokemon2.data.MoveCategory;
import com.pokerogue.helper.pokemon2.data.Type;

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

    public static MoveResponse from(Move move, Integer level, String typeImageFromS3) {
        MoveCategory moveCategory = MoveCategory.findById(move.category());
        Type type1 = Type.findById(move.type());

        return new MoveResponse(
                move.id(),
                move.name(),
                level,
                Integer.parseInt(move.power()),
                Integer.parseInt(move.accuracy()),
                type1.getName(),
                typeImageFromS3,
                moveCategory.getName(),
                moveCategory.getImage()
        );
    }

}
