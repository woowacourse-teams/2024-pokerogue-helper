package com.pokerogue.helper.pokemon2.dto;

import com.pokerogue.helper.pokemon2.data.Move;

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

    public static MoveResponse from(Move move, Integer level) {
        return new MoveResponse(
                move.id(),
                move.name(),
                level,
                Integer.parseInt(move.power()),
                Integer.parseInt(move.accuracy()),
                move.type(),
                "URL" + move.type(),
                move.category(),
                "URL" + move.category()
        );
    }

}
