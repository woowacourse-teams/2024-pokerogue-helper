package com.pokerogue.helper.pokemon.data;

import static com.pokerogue.helper.pokemon.data.PokemonTestFixture.BULBASAUR;
import static com.pokerogue.helper.pokemon.data.PokemonTestFixture.CHARMANDER;
import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.type.data.Type;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PokemonTest {

    @ParameterizedTest
    @MethodSource(value = "typeAndExpectedResult")
    @DisplayName("포켓몬이 가진 타입 일치를 확인한다.")
    void hasSameType(Type type, boolean expectedResult) {
        boolean result = BULBASAUR.hasSameType(type);

        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> typeAndExpectedResult() {
        return Stream.of(Arguments.of(Type.GRASS, true), Arguments.of(Type.FIRE, false));
    }

    @Test
    @DisplayName("포켓몬의 스피드를 비교한다.")
    void isFasterThan() {
        boolean result = BULBASAUR.isFasterThan(CHARMANDER);

        assertThat(result).isFalse();
    }
}
