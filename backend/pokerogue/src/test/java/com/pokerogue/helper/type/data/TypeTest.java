package com.pokerogue.helper.type.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "polla", "jdj"})
    @DisplayName("존재하지 않는 타입인 경우 에러를 발생한다.")
    void convertFrom_WhenNotExist(String inputType) {
        assertThatThrownBy(() -> Type.convertFrom(inputType))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.TYPE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("일치하는 타입을 찾아온다.")
    void convertFrom() {
        assertThat(Type.convertFrom("dragon")).isEqualTo(Type.DRAGON);
    }
}
