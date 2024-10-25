package com.pokerogue.helper.type.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.type.data.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TypeReadConverterTest {

    @Test
    @DisplayName("들어온 값을 Type enum으로 변화한다.")
    void convert() {
        TypeReadConverter typeReadConverter = new TypeReadConverter();

        assertThat(typeReadConverter.convert("fire")).isEqualTo(Type.FIRE);
    }
}
