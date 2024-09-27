package com.pokerogue.helper.battle.service;

import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.type.data.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class WeatherMultiplierProvider {

    private static final Map<Integer, BattleMultiplier> WEATHER_MULTIPLIERS = new HashMap<>();

    static {
        WEATHER_MULTIPLIERS.put(Objects.hash(Weather.SUNNY, Type.FIRE), BattleMultiplier.STRONG_MULTIPLIER);
        WEATHER_MULTIPLIERS.put(Objects.hash(Weather.SUNNY, Type.WATER), BattleMultiplier.WEAK_MULTIPLIER);
        WEATHER_MULTIPLIERS.put(Objects.hash(Weather.HARSH_SUN, Type.FIRE), BattleMultiplier.STRONG_MULTIPLIER);
        WEATHER_MULTIPLIERS.put(Objects.hash(Weather.HARSH_SUN, Type.WATER), BattleMultiplier.ZERO_MULTIPLIER);
        WEATHER_MULTIPLIERS.put(Objects.hash(Weather.RAIN, Type.WATER), BattleMultiplier.STRONG_MULTIPLIER);
        WEATHER_MULTIPLIERS.put(Objects.hash(Weather.RAIN, Type.FIRE), BattleMultiplier.WEAK_MULTIPLIER);
        WEATHER_MULTIPLIERS.put(Objects.hash(Weather.HEAVY_RAIN, Type.WATER), BattleMultiplier.STRONG_MULTIPLIER);
        WEATHER_MULTIPLIERS.put(Objects.hash(Weather.HEAVY_RAIN, Type.FIRE), BattleMultiplier.ZERO_MULTIPLIER);
    }

    public BattleMultiplier getByAttackMoveType(Weather weather, Type attackMoveType) {
        int hashCode = Objects.hash(weather, attackMoveType);

        return WEATHER_MULTIPLIERS.getOrDefault(hashCode, BattleMultiplier.DEFAULT_MULTIPLIER);
    }
}
