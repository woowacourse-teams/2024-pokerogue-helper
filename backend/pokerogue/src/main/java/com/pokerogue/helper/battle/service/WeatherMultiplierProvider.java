package com.pokerogue.helper.battle.service;

import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.type.data.Type;
import org.springframework.stereotype.Component;

@Component
public class WeatherMultiplierProvider {

    public BattleMultiplier getByAttackMoveType(Weather weather, Type attackMoveType) {
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

        return BattleMultiplier.DEFAULT_MULTIPLIER;
    }

    private BattleMultiplier getSunnyAttackTypeMultiplier(Type attackMoveType) {
        if (attackMoveType == Type.FIRE) {
            return BattleMultiplier.STRONG_MULTIPLIER;
        }
        if (attackMoveType == Type.WATER) {
            return BattleMultiplier.WEAK_MULTIPLIER;
        }

        return BattleMultiplier.DEFAULT_MULTIPLIER;
    }

    private BattleMultiplier getHarshSunAttackTypeMultiplier(Type attackMoveType) {
        if (attackMoveType == Type.FIRE) {
            return BattleMultiplier.STRONG_MULTIPLIER;
        }
        if (attackMoveType == Type.WATER) {
            return BattleMultiplier.ZERO_MULTIPLIER;
        }

        return BattleMultiplier.DEFAULT_MULTIPLIER;
    }

    private BattleMultiplier getRainAttackTypeMultiplier(Type attackMoveType) {
        if (attackMoveType == Type.WATER) {
            return BattleMultiplier.STRONG_MULTIPLIER;
        }
        if (attackMoveType == Type.FIRE) {
            return BattleMultiplier.WEAK_MULTIPLIER;
        }

        return BattleMultiplier.DEFAULT_MULTIPLIER;
    }

    private BattleMultiplier getHeavyRainAttackTypeMultiplier(Type attackMoveType) {
        if (attackMoveType == Type.WATER) {
            return BattleMultiplier.STRONG_MULTIPLIER;
        }
        if (attackMoveType == Type.FIRE) {
            return BattleMultiplier.ZERO_MULTIPLIER;
        }

        return BattleMultiplier.DEFAULT_MULTIPLIER;
    }
}
