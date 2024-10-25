package com.pokerogue.helper.move.data;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum MoveFlag {

    NONE("none"),
    MAKES_CONTACT("makes_contact"),
    IGNORE_PROTECT("ignore_protect"),
    IGNORE_VIRTUAL("ignore_virtual"),
    SOUND_BASED("sound_based"),
    HIDE_USER("hide_user"),
    HIDE_TARGET("hide_target"),
    BITING_MOVE("biting_move"),
    PULSE_MOVE("pulse_move"),
    PUNCHING_MOVE("punching_move"),
    SLICING_MOVE("slicing_move"),
    RECKLESS_MOVE("reckless_move"),
    BALLBOMB_MOVE("ballbomb_move"),
    POWDER_MOVE("powder_move"),
    DANCE_MOVE("dance_move"),
    WIND_MOVE("wind_move"),
    TRIAGE_MOVE("triage_move"),
    IGNORE_ABILITIES("ignore_abilities"),
    CHECK_ALL_HITS("check_all_hits"),
    IGNORE_SUBSTITUTE("ignore_substitute"),
    REDIRECT_COUNTER("redirect_counter"),
    ;

    private final String id;

    MoveFlag(String id) {
        this.id = id;
    }

    public static MoveFlag convertFrom(String flagData) {
        return Arrays.stream(MoveFlag.values())
                .filter(moveFlag -> moveFlag.id.equals(flagData))
                .findAny()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_FLAG_NOT_FOUND));
    }
}
