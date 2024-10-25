package com.pokerogue.helper.move.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.move.data.MoveFlag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FlagConverterTest {

    @Test
    @DisplayName("들어온 값을 Flag Enum으로 변화한다.")
    void convert() {
        FlagConverter flagConverter = new FlagConverter();

        assertThat(flagConverter.convert("dance_move")).isEqualTo(MoveFlag.DANCE_MOVE);
    }
}
