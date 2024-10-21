package com.pokerogue.helper.battle;

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

    public static MoveResponse from(BattleMove battleMove) {
        Type type = battleMove.type();
        MoveCategory moveCategory = battleMove.category();

        return new MoveResponse(
                battleMove.id(),
                battleMove.name(),
                type.getEngName(),
                type.getImage(),
                moveCategory.getEngName(),
                moveCategory.getImage(),
                battleMove.power(),
                battleMove.accuracy(),
                battleMove.effect()
        );
    }
}
