package com.pokerogue.helper.battle;

public record MoveResponse(
        String id,
        String name,
        String typeLogo,
        String categoryLogo,
        Integer power,
        Integer accuracy,
        String effect
) {

    public static MoveResponse from(Move move) {
        return new MoveResponse(
                move.id(),
                move.name(),
                "dummy",
                "dummy",
                move.power(),
                move.accuracy(),
                move.effect()
        );
    }
}
