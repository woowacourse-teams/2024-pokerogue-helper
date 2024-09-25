package com.pokerogue.helper.battle.service;

import com.pokerogue.helper.battle.data.BattleMove;
import com.pokerogue.helper.battle.BattleMoveRepository;
import com.pokerogue.helper.battle.dto.BattleResultResponse;
import com.pokerogue.helper.battle.dto.MoveResponse;
import com.pokerogue.helper.battle.InMemoryTypeMatchingRepository;
import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.battle.dto.WeatherResponse;
import com.pokerogue.helper.battle.data.InMemoryTypeMatching;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.data.InMemoryPokemon;
import com.pokerogue.helper.pokemon.repository.InMemoryPokemonRepository;
import com.pokerogue.helper.type.data.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final BattleMoveRepository battleMoveRepository;
    private final InMemoryPokemonRepository inMemoryPokemonRepository;
    private final InMemoryTypeMatchingRepository inMemoryTypeMatchingRepository;

    private Map<Integer, List<MoveResponse>> findByDexnumberCache = new HashMap<>();

    public List<WeatherResponse> findWeathers() {
        return Arrays.stream(Weather.values())
                .map(WeatherResponse::from)
                .toList();
    }

    public List<MoveResponse> findMovesByPokemon(String pokemonId) {
        List<String> allMoveIds = new ArrayList<>();
        InMemoryPokemon inMemoryPokemon = inMemoryPokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));

        List<String> moves = new ArrayList<>();
        for (int i = 0; i < inMemoryPokemon.moves().size(); i++) {
            if (i % 2 == 0) {
                moves.add(inMemoryPokemon.moves().get(i));
            }
        }
        allMoveIds.addAll(moves);
        allMoveIds.addAll(inMemoryPokemon.technicalMachineMoves());
        allMoveIds.addAll(inMemoryPokemon.eggMoves());
        List<BattleMove> battleMoves = allMoveIds.stream()
                .distinct()
                .map(this::findMoveById)
                .toList();

        return battleMoves.stream()
                .map(MoveResponse::from)
                .toList();
    }

    public List<MoveResponse> findMovesByPokemon(Integer pokedexNumber) {
        if (findByDexnumberCache.isEmpty()) {
            initFindByDexnumberCache();
        }

        return findByDexnumberCache.get(pokedexNumber);
    }

    private void initFindByDexnumberCache() {
        for (InMemoryPokemon inMemoryPokemon : inMemoryPokemonRepository.findAll().values()) {
            int pokemonId = Integer.parseInt(inMemoryPokemon.speciesId());
            if (!findByDexnumberCache.containsKey(pokemonId)) {
                findByDexnumberCache.put(pokemonId, makeMoveResponse(inMemoryPokemon));
            }
        }
    }

    private List<MoveResponse> makeMoveResponse(InMemoryPokemon inMemoryPokemon) {
        List<String> allMoveIds = new ArrayList<>();
        List<String> moves = new ArrayList<>();
        for (int i = 0; i < inMemoryPokemon.moves().size(); i++) {
            if (i % 2 == 0) {
                moves.add(inMemoryPokemon.moves().get(i));
            }
        }
        allMoveIds.addAll(moves);
        allMoveIds.addAll(inMemoryPokemon.technicalMachineMoves());
        allMoveIds.addAll(inMemoryPokemon.eggMoves());
        List<BattleMove> battleMoves = allMoveIds.stream()
                .distinct()
                .map(this::findMoveById)
                .toList();

        return battleMoves.stream()
                .map(MoveResponse::from)
                .toList();
    }

    private BattleMove findMoveById(String id) {
        return battleMoveRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND));
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
        BattleMove move = battleMoveRepository.findById(myMoveId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));
        Type moveType = move.type();

        double finalAccuracy = calculateAccuracy(move, weather);
        double totalMultiplier = getTotalMultiplier(move, weather, rivalInMemoryPokemon, myInMemoryPokemon);

        return new BattleResultResponse(
                move.power(),
                totalMultiplier,
                finalAccuracy,
                move.name(),
                move.effect(),
                moveType.getKoName(),
                move.category().getName()
        );
    }

    private double calculateAccuracy(BattleMove move, Weather weather) {
        if (weather == Weather.FOG) {
            return (double) move.accuracy() * 0.9;
        }

        return (double) move.accuracy();
    }

    private double getTotalMultiplier(
            BattleMove move,
            Weather weather,
            InMemoryPokemon rivalInMemoryPokemon,
            InMemoryPokemon myInMemoryPokemon) {
        if (!move.isAttackMove()) {
            return 1;
        }
        Type moveType = move.type();
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
                .map(toType -> inMemoryTypeMatchingRepository.findByFromTypeAndToType(moveType, toType)
                        .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_MATCHING_ERROR)))
                .map(InMemoryTypeMatching::result)
                .reduce(1d, (a, b) -> a * b);
    }

    private double getSameTypeAttackBonusMultiplier(Type moveType, InMemoryPokemon rivalInMemoryPokemon) {
        if (rivalInMemoryPokemon.hasSameType(moveType)) {
            return 1.5;
        }

        return 1;
    }

    private double getStringWindMultiplier(Type moveType, List<Type> rivalPokemonTypes, Weather weather) {
        InMemoryTypeMatching inMemoryTypeMatching = inMemoryTypeMatchingRepository.findByFromTypeAndToType(moveType, Type.FLYING)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_MATCHING_ERROR));

        if (weather == Weather.STRONG_WINDS && rivalPokemonTypes.contains(Type.FLYING) && inMemoryTypeMatching.result() == 2) {
            return 0.5;
        }

        return 1;
    }
}
