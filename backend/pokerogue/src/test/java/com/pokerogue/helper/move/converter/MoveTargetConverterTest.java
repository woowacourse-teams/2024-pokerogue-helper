package com.pokerogue.helper.move.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.move.data.MoveTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoveTargetConverterTest {

    @Test
    @DisplayName("들어온 값을 Move Target Enum 으로 변화한다.")
    void convert() {
        MoveTargetConverter moveTargetConverter = new MoveTargetConverter();

        assertThat(moveTargetConverter.convert("user")).isEqualTo(MoveTarget.USER);
    }
}
