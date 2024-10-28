package com.pokerogue.helper.move.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MoveTargetTest {

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "polla", "ald"})
    void convertFrom_WhenNotExist(String inputMoveTarget) {
        assertThatThrownBy(() -> MoveTarget.convertFrom(inputMoveTarget))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.MOVE_TARGET_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("일치하는 MoveTarget을 찾아온다.")
    void convertFrom() {
        assertThat(MoveTarget.convertFrom("all")).isEqualTo(MoveTarget.ALL);
    }
}
