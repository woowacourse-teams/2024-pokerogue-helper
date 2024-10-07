package com.pokerogue.helper.move.data;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;

public enum MoveTarget {

    USER("user"),
    OTHER("other"),
    ALL_OTHERS("all_others"),
    NEAR_OTHER("near_other"),
    ALL_NEAR_OTHERS("all_near_others"),
    NEAR_ENEMY("near_enemy"),
    ALL_NEAR_ENEMIES("all_near_enemies"),
    RANDOM_NEAR_ENEMY("random_near_enemy"),
    ALL_ENEMIES("all_enemies"),
    ATTACKER("attacker"),
    NEAR_ALLY("near_ally"),
    ALLY("ally"),
    USER_OR_NEAR_ALLY("user_or_near_ally"),
    USER_AND_ALLIES("user_and_allies"),
    ALL("all"),
    USER_SIDE("user_side"),
    ENEMY_SIDE("enemy_side"),
    BOTH_SIDES("both_sides"),
    PARTY("party"),
    CURSE("curse"),
    ;

    private final String id;

    MoveTarget(String id) {
        this.id = id;
    }

    public static MoveTarget convertFrom(String moveTargetData) {
        return Arrays.stream(MoveTarget.values())
                .filter(moveTarget -> moveTarget.id.equals(moveTargetData))
                .findAny()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_TARGET_NOT_FOUND));
    }
}
