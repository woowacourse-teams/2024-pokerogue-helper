package com.pokerogue.helper.global.constant;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SortingCriteriaTest {

    @ParameterizedTest
    @MethodSource("sortingCriteriaValues")
    @DisplayName("문자열 값으로 SortingCriteria를 생성한다.")
    void convertFrom(String value, SortingCriteria expected) {
        SortingCriteria actual = SortingCriteria.convertFrom(value);

        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> sortingCriteriaValues() {
        return Stream.of(
                Arguments.of("desc", SortingCriteria.DESCENDING),
                Arguments.of("asc", SortingCriteria.ASCENDING)
        );
    }
}
