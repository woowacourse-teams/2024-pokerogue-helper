package com.pokerogue.helper.battle.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public final class BattleMultiplier {

    public static final BattleMultiplier STRONG_MULTIPLIER = new BattleMultiplier(BigDecimal.valueOf(1.5));
    public static final BattleMultiplier WEAK_MULTIPLIER = new BattleMultiplier(BigDecimal.valueOf(0.5));
    public static final BattleMultiplier ZERO_MULTIPLIER = new BattleMultiplier(BigDecimal.valueOf(0));
    public static final BattleMultiplier DEFAULT_MULTIPLIER = new BattleMultiplier(BigDecimal.valueOf(1));
    private static final Map<BigDecimal, BattleMultiplier> CACHE = new HashMap<>();

    static {
        CACHE.put(STRONG_MULTIPLIER.value, STRONG_MULTIPLIER);
        CACHE.put(WEAK_MULTIPLIER.value, WEAK_MULTIPLIER);
        CACHE.put(ZERO_MULTIPLIER.value, ZERO_MULTIPLIER);
        CACHE.put(DEFAULT_MULTIPLIER.value, DEFAULT_MULTIPLIER); // Todo: 캐싱 추가
    }

    private final BigDecimal value;

    private BattleMultiplier(BigDecimal value) {
        this.value = value;
    }

    public static BattleMultiplier valueOf(BigDecimal value) {
        return CACHE.getOrDefault(value, new BattleMultiplier(value));
    }

    public static BattleMultiplier multiply(BattleMultiplier... others) {
        BigDecimal result = Arrays.stream(others)
                .map(BattleMultiplier::getValue)
                .reduce(DEFAULT_MULTIPLIER.value, (a, b) -> a.multiply(b));

        return BattleMultiplier.valueOf(result);
    }

    public BattleMultiplier multiply(BattleMultiplier other) {
        BigDecimal result = this.value.multiply(other.value);

        return BattleMultiplier.valueOf(result);
    }

    public double getDoubleValue() {
        return this.value.doubleValue();
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

        return this.value.compareTo(that.value) == 0;
    }

    @Override
    public int hashCode() {
        return this.value.stripTrailingZeros().hashCode();
    }
}
