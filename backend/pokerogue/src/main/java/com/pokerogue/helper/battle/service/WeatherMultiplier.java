package com.pokerogue.helper.battle.service;

import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.type.data.Type;
import org.springframework.stereotype.Component;

@Component
public class WeatherMultiplier extends BattleMultiplier {

    public double getByAttackMoveType(Weather weather, Type attackMoveType) {
        if (weather == Weather.SUNNY) {
            return getSunnyAttackTypeMultiplier(attackMoveType);
        }
        if (weather == Weather.HARSH_SUN) {
            return getHarshSunAttackTypeMultiplier(attackMoveType);
        }
        if (weather == Weather.RAIN) {
            return getRainAttackTypeMultiplier(attackMoveType);
        }
        if (weather == Weather.HEAVY_RAIN) {
            return getHeavyRainAttackTypeMultiplier(attackMoveType);
        }
        return 1;
    }

    private double getSunnyAttackTypeMultiplier(Type attackMoveType) {
        if (attackMoveType == Type.FIRE) {
            return STRONG_MULTIPLIER;
        }
        if (attackMoveType == Type.WATER) {
            return WEAK_MULTIPLIER;
        }
        return DEFAULT_MULTIPLIER;
    }

    private double getHarshSunAttackTypeMultiplier(Type attackMoveType) {
        if (attackMoveType == Type.FIRE) {
            return STRONG_MULTIPLIER;
        }
        if (attackMoveType == Type.WATER) {
            return ZERO_MULTIPLIER;
        }
        return DEFAULT_MULTIPLIER;
    }

    private double getRainAttackTypeMultiplier(Type attackMoveType) {
        if (attackMoveType == Type.WATER) {
            return STRONG_MULTIPLIER;
        }
        if (attackMoveType == Type.FIRE) {
            return WEAK_MULTIPLIER;
        }
        return DEFAULT_MULTIPLIER;
    }

    private double getHeavyRainAttackTypeMultiplier(Type attackMoveType) {
        if (attackMoveType == Type.WATER) {
            return STRONG_MULTIPLIER;
        }
        if (attackMoveType == Type.FIRE) {
            return ZERO_MULTIPLIER;
        }
        return DEFAULT_MULTIPLIER;
    }
}
