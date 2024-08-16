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

    public static MoveResponse of(Move move, String typeLogo, String categoryLogo) {
        return new MoveResponse(
                move.id(),
                move.name(),
                typeLogo,
                categoryLogo,
                move.power(),
                move.accuracy(),
                move.effect()
        );
    }
}
