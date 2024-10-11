package com.pokerogue.helper.battle.service;

import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.type.data.Type;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BattleCalculator {

    private static final double FOG_ACCURACY_EFFECT = 0.9;
    private static final BattleMultiplier[] EMPTY_BATTLE_MULTIPLIER_ARRAY = new BattleMultiplier[0];

    private final TypeMultiplierProvider typeMultiplierProvider;
    private final WeatherMultiplierProvider weatherMultiplierProvider;

    public double calculateAccuracy(Move move, Weather weather) {
        if (weather == Weather.FOG) {
            return move.getAccuracy() * FOG_ACCURACY_EFFECT;
        }

        return move.getAccuracy();
    }

    public double calculateTotalMultiplier(
            Move move,
            Weather weather,
            Pokemon rivalPokemon,
            Pokemon myPokemon) {
        if (!move.isAttackMove()) {
            return BattleMultiplier.DEFAULT_MULTIPLIER.getDoubleValue();
        }

        Type moveType = move.getType();
        List<Type> rivalPokemonTypes = rivalPokemon.getTypes();
        BattleMultiplier weatherMultiplier = getWeatherMultiplier(weather, moveType);
        BattleMultiplier sameTypeBonusMultiplier = getSameTypeBonusMultiplier(moveType, myPokemon);
        BattleMultiplier typeMatchingMultiplier = getTypeMatchingMultiplier(moveType, rivalPokemonTypes);

        BattleMultiplier totalMultiplier = BattleMultiplier.multiply(
                weatherMultiplier, sameTypeBonusMultiplier, typeMatchingMultiplier);
        if (weather == Weather.STRONG_WINDS) {
            totalMultiplier = applyStrongWindMultiplier(totalMultiplier, moveType, rivalPokemonTypes);
        }

        return totalMultiplier.getDoubleValue();
    }

    private BattleMultiplier getWeatherMultiplier(Weather weather, Type moveType) {
        return weatherMultiplierProvider.getByAttackMoveType(weather, moveType);
    }

    private BattleMultiplier getSameTypeBonusMultiplier(Type moveType, Pokemon myPokemon) {
        return typeMultiplierProvider.getBySameTypeAttackBonus(moveType, myPokemon);
    }

    private BattleMultiplier getTypeMatchingMultiplier(Type moveType, List<Type> rivalPokemonTypes) {
        List<BattleMultiplier> typeMatchingMultipliers = typeMultiplierProvider.getAllByTypeMatchings(moveType,
                rivalPokemonTypes);
        return BattleMultiplier.multiply(typeMatchingMultipliers.toArray(EMPTY_BATTLE_MULTIPLIER_ARRAY));
    }

    private BattleMultiplier applyStrongWindMultiplier(BattleMultiplier totalMultiplier, Type moveType,
                                                       List<Type> rivalPokemonTypes) {
        BattleMultiplier strongWindMultiplier = typeMultiplierProvider.getByStrongWind(moveType, rivalPokemonTypes);
        return BattleMultiplier.multiply(totalMultiplier, strongWindMultiplier);
    }

    public boolean decidePreemptiveAttack(Pokemon rivalPokemon, Pokemon myPokemon) {
        return myPokemon.isFasterThan(rivalPokemon);
    }
}
