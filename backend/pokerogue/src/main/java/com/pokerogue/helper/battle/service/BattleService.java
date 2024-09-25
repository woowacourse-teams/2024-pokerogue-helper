package com.pokerogue.helper.battle.service;

import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.battle.dto.BattleResultResponse;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.repository.MoveRepository;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleService {

    private static final double FOG_ACCURACY_EFFECT = 0.9;

    private final MoveRepository moveRepository;
    private final PokemonRepository pokemonRepository;
    private final TypeMultiplierProvider typeMultiplierProvider;

    public BattleResultResponse calculateBattleResult(
            String weatherId,
            String myPokemonId,
            String rivalPokemonId,
            String myMoveId) {
        Weather weather = Weather.findById(weatherId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.WEATHER_NOT_FOUND));
        Pokemon myPokemon = pokemonRepository.findById(myPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        Pokemon rivalPokemon = pokemonRepository.findById(rivalPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        Move move = moveRepository.findById(myMoveId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND));
        Type moveType = Type.valueOf(move.getType().toUpperCase()); // Todo

        double finalAccuracy = calculateAccuracy(move, weather);
        double totalMultiplier = calculateTotalMultiplier(move, weather, rivalPokemon, myPokemon);

        return new BattleResultResponse(
                move.getPower(),
                totalMultiplier,
                finalAccuracy,
                move.getName(),
                move.getEffect(),
                moveType.getKoName(),
                move.getMoveCategory()
        );
    }

    private double calculateAccuracy(Move move, Weather weather) {
        if (weather == Weather.FOG) {
            return (double) move.getAccuracy() * FOG_ACCURACY_EFFECT;
        }

        return move.getAccuracy();
    }

    private double calculateTotalMultiplier(
            Move move,
            Weather weather,
            Pokemon rivalPokemon,
            Pokemon myPokemon) {
        if (!move.isAttackMove()) {
            return BattleMultiplier.DEFAULT_MULTIPLIER.getValue();
        }

        Type moveType = Type.valueOf(move.getType().toUpperCase()); // Todo
        List<Type> types = rivalPokemon.getTypes()
                .stream() // Todo
                .map(String::toUpperCase)
                .map(Type::valueOf)
                .toList();
        BattleMultiplier weatherMultiplier = weather.getBattleMultiplierByAttackMoveType(moveType);
        BattleMultiplier sameTypeBonusMultiplier = typeMultiplierProvider.getBySameTypeAttackBonus(moveType, myPokemon);
        List<BattleMultiplier> typeMatchingMultipliers = typeMultiplierProvider.getAllByTypeMatchings(moveType, types);

        BattleMultiplier typeMatchingMultiplier = BattleMultiplier.multiply(
                typeMatchingMultipliers.toArray(new BattleMultiplier[0]));
        BattleMultiplier totalMultiplier = BattleMultiplier.multiply(weatherMultiplier, sameTypeBonusMultiplier,
                typeMatchingMultiplier);
        if (weather == Weather.STRONG_WINDS) {
            BattleMultiplier strongWindMultiplier = typeMultiplierProvider.getByStrongWind(moveType, types);
            totalMultiplier.multiply(strongWindMultiplier);
        }

        return totalMultiplier.getValue();
    }
}
