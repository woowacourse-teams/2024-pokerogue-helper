package com.pokerogue.helper.battle.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.type.data.Type;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WeatherMultiplierProviderTest {

    private final WeatherMultiplierProvider weatherMultiplierProvider = new WeatherMultiplierProvider();

    @ParameterizedTest
    @MethodSource(value = "multiplierConditions")
    @DisplayName("공격 기술 타입에 따른 배틀 날씨 배수를 구한다.")
    void getAttackTypeMultiplier(Weather weather, Type attackMoveType, BattleMultiplier expectedMultiplier) {

        BattleMultiplier multiplier = weatherMultiplierProvider.getByAttackMoveType(weather, attackMoveType);

        assertThat(multiplier).isEqualTo(expectedMultiplier);
    }

    private static Stream<Arguments> multiplierConditions() {
        return Stream.of(
                Arguments.of(Weather.SUNNY, Type.FIRE, BattleMultiplier.STRONG_MULTIPLIER),
                Arguments.of(Weather.HARSH_SUN, Type.FIRE, BattleMultiplier.STRONG_MULTIPLIER),
                Arguments.of(Weather.RAIN, Type.FIRE, BattleMultiplier.WEAK_MULTIPLIER),
                Arguments.of(Weather.HEAVY_RAIN, Type.FIRE, BattleMultiplier.ZERO_MULTIPLIER),
                Arguments.of(Weather.SUNNY, Type.WATER, BattleMultiplier.WEAK_MULTIPLIER),
                Arguments.of(Weather.HARSH_SUN, Type.WATER, BattleMultiplier.ZERO_MULTIPLIER),
                Arguments.of(Weather.RAIN, Type.WATER, BattleMultiplier.STRONG_MULTIPLIER),
                Arguments.of(Weather.HEAVY_RAIN, Type.WATER, BattleMultiplier.STRONG_MULTIPLIER),
                Arguments.of(Weather.SUNNY, Type.BUG, BattleMultiplier.DEFAULT_MULTIPLIER),
                Arguments.of(Weather.FOG, Type.FIRE, BattleMultiplier.DEFAULT_MULTIPLIER)
        );
    }
}
