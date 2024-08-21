package com.pokerogue.helper.battle;

public record BattleMove(
        String id,
        String name,
        String nameAppend,
        String effect,
        Type type,
        String defaultTypeId,
        MoveCategory category,
        String moveTarget,
        Integer power,
        Integer accuracy,
        Integer pp,
        Integer chance,
        Integer priority,
        Integer generation,
        String flags
) {
}