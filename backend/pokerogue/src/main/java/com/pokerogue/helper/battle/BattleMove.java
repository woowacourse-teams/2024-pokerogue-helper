package com.pokerogue.helper.battle;

import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.type.data.Type;

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

    public boolean isAttackMove() {
        return this.category != MoveCategory.STATUS;
    }
}
