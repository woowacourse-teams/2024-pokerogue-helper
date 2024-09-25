package com.pokerogue.helper.battle.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;

@Getter
public final class BattleMultiplier {

    public static final BattleMultiplier STRONG_MULTIPLIER = new BattleMultiplier(1.5);
    public static final BattleMultiplier WEAK_MULTIPLIER = new BattleMultiplier(0.5);
    public static final BattleMultiplier ZERO_MULTIPLIER = new BattleMultiplier(0);
    public static final BattleMultiplier DEFAULT_MULTIPLIER = new BattleMultiplier(1);
    private static final Map<Double, BattleMultiplier> CACHE = new HashMap<>();

    static {
        CACHE.put(STRONG_MULTIPLIER.value, STRONG_MULTIPLIER);
        CACHE.put(WEAK_MULTIPLIER.value, WEAK_MULTIPLIER);
        CACHE.put(ZERO_MULTIPLIER.value, ZERO_MULTIPLIER);
        CACHE.put(DEFAULT_MULTIPLIER.value, DEFAULT_MULTIPLIER); // Todo: 캐싱 추가
    }

    private final double value;

    private BattleMultiplier(double value) {
        this.value = value;
    }

    public static BattleMultiplier valueOf(double value) {
        return CACHE.getOrDefault(value, new BattleMultiplier(value));
    }

    public static BattleMultiplier multiply(BattleMultiplier... others) {
        double result = Arrays.stream(others)
                .map(BattleMultiplier::getValue)
                .reduce(1d, (a, b) -> a * b);
        return BattleMultiplier.valueOf(result);
    }

    public BattleMultiplier multiply(BattleMultiplier other) {
        double result = this.value * other.value;
        return BattleMultiplier.valueOf(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BattleMultiplier that = (BattleMultiplier) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
