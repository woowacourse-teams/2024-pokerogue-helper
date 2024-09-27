package com.pokerogue.helper.battle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MoveCategoryTest {

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "polla", "po"})
    @DisplayName("존재하지 않는 MoveCategory인 경우 에러를 발생한다.")
    void convertFrom_WhenNotExist(String inputCategory) {
        assertThatThrownBy(() -> MoveCategory.convertFrom(inputCategory))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.MOVE_CATEGORY_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("일치하는 MoveCategory를 찾는다.")
    void convertFrom() {
        assertThat(MoveCategory.convertFrom("special")).isEqualTo(MoveCategory.SPECIAL);
    }
}
