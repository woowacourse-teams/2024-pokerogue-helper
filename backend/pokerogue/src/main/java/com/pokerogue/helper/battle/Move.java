package com.pokerogue.helper.battle;

public record Move(
        String id,
        String name,
        String nameAppend,
        String effect,
        String type,
        String defaultTypeId,
        String category,
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
