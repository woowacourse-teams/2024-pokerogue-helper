package com.pokerogue.helper.move.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.move.data.MoveCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoveCategoryConverterTest {

    @Test
    @DisplayName("들어온 값을 MoveCategory Enum으로 변화한다.")
    void convert() {
        MoveCategoryConverter moveCategoryConverter = new MoveCategoryConverter();

        assertThat(moveCategoryConverter.convert("special")).isEqualTo(MoveCategory.SPECIAL);
    }
}
