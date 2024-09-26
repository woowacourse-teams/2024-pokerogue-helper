package com.pokerogue.helper.battle.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class BattleMultiplierTest {

    @Test
    @DisplayName("배틀배수끼리 동등하다.")
    void hasSemanticEquality() {
        BigDecimal multiplierValue = BigDecimal.valueOf(0.3).add(BigDecimal.valueOf(0.3).add(BigDecimal.valueOf(0.3)));
        BattleMultiplier multiplier = BattleMultiplier.valueOf(multiplierValue);
        BigDecimal otherMultiplierValue = BigDecimal.valueOf(0.9);
        BattleMultiplier otherMultiplier = BattleMultiplier.valueOf(otherMultiplierValue);

        assertThat(multiplier).isEqualTo(otherMultiplier);
    }

    @ParameterizedTest
    @MethodSource(value = "constantBattleMultipliers")
    @DisplayName("배틀 배수 상수는 캐싱한다.")
    void cacheConstants(BattleMultiplier constantBattleMultiplier) {
        BigDecimal constantValue = constantBattleMultiplier.getValue();

        BattleMultiplier sameValueMultiplier = BattleMultiplier.valueOf(constantValue);

        assertThat(sameValueMultiplier).isSameAs(constantBattleMultiplier);
    }

    private static Stream<BattleMultiplier> constantBattleMultipliers() {
        return Stream.of(
                BattleMultiplier.ZERO_MULTIPLIER,
                BattleMultiplier.WEAK_MULTIPLIER,
                BattleMultiplier.STRONG_MULTIPLIER,
                BattleMultiplier.DEFAULT_MULTIPLIER
        );
    }

    @Test
    @DisplayName("배틀 배수 두 개를 곱한다.")
    void multiply() {
        BattleMultiplier multiplier = BattleMultiplier.valueOf(BigDecimal.valueOf(2));
        BattleMultiplier otherMultiplier = BattleMultiplier.valueOf(BigDecimal.valueOf(1.5));

        BattleMultiplier multipliedResult = multiplier.multiply(otherMultiplier);

        BigDecimal expectedResultValue = BigDecimal.valueOf(3);
        assertThat(multipliedResult).isEqualTo(BattleMultiplier.valueOf(expectedResultValue));
    }

    @Test
    @DisplayName("배틀 배수 여러개를 곱한다.")
    void multiplyMultiple() {
        BattleMultiplier alpha = BattleMultiplier.valueOf(BigDecimal.valueOf(2));
        BattleMultiplier beta = BattleMultiplier.valueOf(BigDecimal.valueOf(1.5));
        BattleMultiplier gamma = BattleMultiplier.valueOf(BigDecimal.valueOf(0.5));

        BattleMultiplier multipliedResult = BattleMultiplier.multiply(alpha, beta, gamma);

        BigDecimal expectedResultValue = BigDecimal.valueOf(1.5);
        assertThat(multipliedResult).isEqualTo(BattleMultiplier.valueOf(expectedResultValue));
    }
}
