package com.pokerogue.helper.battle.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.type.data.Type;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WeatherMultiplierTest {
    private final WeatherMultiplier weatherMultiplier = new WeatherMultiplier();

    @ParameterizedTest
    @MethodSource(value = "multiplierConditions")
    @DisplayName("공격 기술 타입에 따른 배틀 날씨 배수를 구한다.")
    void getAttackTypeMultiplier(Weather weather, Type attackMoveType, double expectedMultiplier) {

        double multiplier = weatherMultiplier.getByAttackMoveType(weather, attackMoveType);

        assertThat(multiplier).isEqualTo(expectedMultiplier);
    }

    private static Stream<Arguments> multiplierConditions() {
        return Stream.of(
                Arguments.of(Weather.SUNNY, Type.FIRE, 1.5),
                Arguments.of(Weather.HARSH_SUN, Type.FIRE, 1.5),
                Arguments.of(Weather.RAIN, Type.FIRE, 0.5),
                Arguments.of(Weather.HEAVY_RAIN, Type.FIRE, 0),
                Arguments.of(Weather.SUNNY, Type.WATER, 0.5),
                Arguments.of(Weather.HARSH_SUN, Type.WATER, 0),
                Arguments.of(Weather.RAIN, Type.WATER, 1.5),
                Arguments.of(Weather.HEAVY_RAIN, Type.WATER, 1.5),
                Arguments.of(Weather.SUNNY, Type.BUG, 1),
                Arguments.of(Weather.FOG, Type.FIRE, 1)
        );
    }
}
