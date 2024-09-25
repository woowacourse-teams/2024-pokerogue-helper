package com.pokerogue.helper.move.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MoveFlagTest {

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "polla", "pp"})
    void convertFrom_WhenNotExist(String inputMoveFlag) {
        assertThatThrownBy(() -> MoveFlag.convertFrom(inputMoveFlag))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.MOVE_FLAG_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("일치하는 Move Flag를 찾아온다.")
    void convertFrom() {
        assertThat(MoveFlag.convertFrom("dance_move")).isEqualTo(MoveFlag.DANCE_MOVE);
    }
}
