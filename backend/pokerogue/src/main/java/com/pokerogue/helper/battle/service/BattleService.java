package com.pokerogue.helper.battle.service;

import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.battle.dto.BattleResultResponseV1;
import com.pokerogue.helper.battle.dto.BattleResultResponseV2;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.repository.MoveRepository;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final MoveRepository moveRepository;
    private final PokemonRepository pokemonRepository;
    private final BattleCalculator battleCalculator;

    public BattleResultResponseV1 calculateBattleResultV1(
            String weatherId,
            String myPokemonId,
            String rivalPokemonId,
            String myMoveId) {
        BattleResult battleResult = calculateBattleResult(weatherId, myPokemonId, rivalPokemonId, myMoveId);
        return BattleResultResponseV1.from(battleResult.move, battleResult.totalMultiplier, battleResult.finalAccuracy);
    }

    public BattleResultResponseV2 calculateBattleResultV2(
            String weatherId,
            String myPokemonId,
            String rivalPokemonId,
            String myMoveId) {
        BattleResult battleResult = calculateBattleResult(weatherId, myPokemonId, rivalPokemonId, myMoveId);
        boolean isPreemptive = battleCalculator.decidePreemptiveAttack(battleResult.rivalPokemon, battleResult.myPokemon);

        return BattleResultResponseV2.from(
                battleResult.move,
                battleResult.totalMultiplier,
                battleResult.finalAccuracy,
                isPreemptive
        );
    }

    private BattleResult calculateBattleResult(
            String weatherId,
            String myPokemonId,
            String rivalPokemonId,
            String myMoveId
    ) {
        Weather weather = Weather.findById(weatherId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.WEATHER_NOT_FOUND));
        Pokemon myPokemon = pokemonRepository.findById(myPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        Pokemon rivalPokemon = pokemonRepository.findById(rivalPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        Move move = moveRepository.findById(myMoveId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND));

        double finalAccuracy = battleCalculator.calculateAccuracy(move, weather);
        double totalMultiplier = battleCalculator.calculateTotalMultiplier(move, weather, rivalPokemon, myPokemon);

        return new BattleResult(weather, myPokemon, rivalPokemon, move, finalAccuracy, totalMultiplier);
    }

    private static class BattleResult {
        Weather weather;
        Pokemon myPokemon;
        Pokemon rivalPokemon;
        Move move;
        double finalAccuracy;
        double totalMultiplier;

        BattleResult(
                Weather weather,
                Pokemon myPokemon,
                Pokemon rivalPokemon,
                Move move,
                double finalAccuracy,
                double totalMultiplier
        ) {
            this.weather = weather;
            this.myPokemon = myPokemon;
            this.rivalPokemon = rivalPokemon;
            this.move = move;
            this.finalAccuracy = finalAccuracy;
            this.totalMultiplier = totalMultiplier;
        }
    }
}
