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

    public static MoveResponse of(BattleMove battleMove, String typeLogo, String categoryLogo) {
        return new MoveResponse(
                battleMove.id(),
                battleMove.name(),
                typeLogo,
                categoryLogo,
                battleMove.power(),
                battleMove.accuracy(),
                battleMove.effect()
        );
    }
}
