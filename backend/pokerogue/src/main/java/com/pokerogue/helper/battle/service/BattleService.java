package com.pokerogue.helper.battle.service;

import com.pokerogue.helper.battle.data.TypeMatching;
import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.battle.dto.BattleResultResponse;
import com.pokerogue.helper.battle.dto.WeatherResponse;
import com.pokerogue.helper.battle.repository.TypeMatchingRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.repository.MoveRepository;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.type.data.Type;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final MoveRepository moveRepository;
    private final PokemonRepository pokemonRepository;
    private final TypeMatchingRepository typeMatchingRepository;

    public List<WeatherResponse> findWeathers() {
        return Arrays.stream(Weather.values())
                .map(WeatherResponse::from)
                .toList();
    }

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
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));
        Type moveType = Type.valueOf(move.getType().toUpperCase()); // Todo

        double finalAccuracy = calculateAccuracy(move, weather);
        double totalMultiplier = getTotalMultiplier(move, weather, rivalPokemon, myPokemon);

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
            return (double) move.getAccuracy() * 0.9;
        }

        return (double) move.getAccuracy();
    }

    private double getTotalMultiplier(
            Move move,
            Weather weather,
            Pokemon rivalPokemon,
            Pokemon myPokemon) {
        if (!move.isAttackMove()) {
            return 1;
        }
        Type moveType = Type.valueOf(move.getType()); // Todo
        double weatherMultiplier = getWeatherMultiplier(moveType, weather);
        List<Type> types = rivalPokemon.getTypes()
                .stream()
                .map(String::toUpperCase) // Todo
                .map(Type::valueOf)
                .toList();
        double typeMatchingMultiplier = getTypeMatchingMultiplier(moveType, types);
        double sameTypeBonusMultiplier = getSameTypeAttackBonusMultiplier(moveType, myPokemon);
        double stringWindMultiplier = getStringWindMultiplier(moveType, types, weather);

        return weatherMultiplier * typeMatchingMultiplier * sameTypeBonusMultiplier * stringWindMultiplier;
    }

    private double getWeatherMultiplier(Type moveType, Weather weather) {
        if (moveType == Type.FIRE) {
            if (weather == Weather.SUNNY || weather == Weather.HARSH_SUN) {
                return 1.5;
            }
            if (weather == Weather.RAIN) {
                return 0.5;
            }
            if (weather == Weather.HEAVY_RAIN) {
                return 0;
            }
        }
        if (moveType == Type.WATER) {
            if (weather == Weather.SUNNY) {
                return 0.5;
            }
            if (weather == Weather.HARSH_SUN) {
                return 0;
            }
            if (weather == Weather.RAIN || weather == Weather.HEAVY_RAIN) {
                return 1.5;
            }
        }

        return 1;
    }

    private double getTypeMatchingMultiplier(Type moveType, List<Type> rivalPokemonTypes) {
        return rivalPokemonTypes.stream()
                .map(toType -> typeMatchingRepository.findByFromAndTo(moveType.getName(), toType.getName())
                        .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_MATCHING_ERROR)))
                .map(TypeMatching::getResult)
                .reduce(1d, (a, b) -> a * b);
    }

    private double getSameTypeAttackBonusMultiplier(Type moveType, Pokemon rivalPokemon) {
        if (rivalPokemon.hasSameType(moveType)) {
            return 1.5;
        }

        return 1;
    }

    private double getStringWindMultiplier(Type moveType, List<Type> rivalPokemonTypes, Weather weather) {
        TypeMatching typeMatching = typeMatchingRepository.findByFromAndTo(moveType.getName(), Type.FLYING.getName())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_MATCHING_ERROR));

        if (weather == Weather.STRONG_WINDS && rivalPokemonTypes.contains(Type.FLYING)
                && typeMatching.getResult() == 2) {
            return 0.5;
        }

        return 1;
    }
}
