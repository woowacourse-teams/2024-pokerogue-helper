package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.move.repository.MoveRepository;
import com.pokerogue.helper.pokemon.data.InMemoryPokemon;
import com.pokerogue.helper.pokemon.repository.InMemoryPokemonRepository;
import com.pokerogue.helper.type.data.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final MoveRepository moveRepository;
    private final InMemoryPokemonRepository inMemoryPokemonRepository;
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
        InMemoryPokemon myInMemoryPokemon = inMemoryPokemonRepository.findById(myPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        InMemoryPokemon rivalInMemoryPokemon = inMemoryPokemonRepository.findById(rivalPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        Move move = moveRepository.findById(myMoveId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));
        Type moveType = Type.findByEngName(move.getType())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));

        double finalAccuracy = calculateAccuracy(move, weather);
        double totalMultiplier = getTotalMultiplier(move, weather, rivalInMemoryPokemon, myInMemoryPokemon);

        MoveCategory moveCategory = MoveCategory.findByEngName(move.getMoveCategory())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));

        return new BattleResultResponse(
                move.getPower(),
                totalMultiplier,
                finalAccuracy,
                move.getKoName(),
                move.getEffect(),
                moveType.getKoName(),
                moveCategory.getName()
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
            InMemoryPokemon rivalInMemoryPokemon,
            InMemoryPokemon myInMemoryPokemon) {
        if (!isAttackMove(move)) {
            return 1;
        }
        Type moveType = Type.findByEngName(move.getType())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
        double weatherMultiplier = getWeatherMultiplier(moveType, weather);
        List<Type> types = new ArrayList<>();
        if (!rivalInMemoryPokemon.firstType().isEmpty()) {
            types.add(Type.findByEngName(rivalInMemoryPokemon.firstType())
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND)));
        }
        if (!rivalInMemoryPokemon.secondType().isEmpty()) {
            types.add(Type.findByEngName(rivalInMemoryPokemon.secondType())
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND)));
        }
        double typeMatchingMultiplier = getTypeMatchingMultiplier(moveType, types);
        double sameTypeBonusMultiplier = getSameTypeAttackBonusMultiplier(moveType, myInMemoryPokemon);
        double stringWindMultiplier = getStringWindMultiplier(moveType, types, weather);

        return weatherMultiplier * typeMatchingMultiplier * sameTypeBonusMultiplier * stringWindMultiplier;
    }

    private boolean isAttackMove(Move move) {
        return !move.getMoveCategory().equals("status");
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
                .map(toType -> typeMatchingRepository.findByFromTypeAndToType(moveType, toType)
                        .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_MATCHING_ERROR)))
                .map(TypeMatching::result)
                .reduce(1d, (a, b) -> a * b);
    }

    private double getSameTypeAttackBonusMultiplier(Type moveType, InMemoryPokemon rivalInMemoryPokemon) {
        if (rivalInMemoryPokemon.hasSameType(moveType)) {
            return 1.5;
        }

        return 1;
    }

    private double getStringWindMultiplier(Type moveType, List<Type> rivalPokemonTypes, Weather weather) {
        TypeMatching typeMatching = typeMatchingRepository.findByFromTypeAndToType(moveType, Type.FLYING)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_MATCHING_ERROR));

        if (weather == Weather.STRONG_WINDS && rivalPokemonTypes.contains(Type.FLYING) && typeMatching.result() == 2) {
            return 0.5;
        }

        return 1;
    }
}
