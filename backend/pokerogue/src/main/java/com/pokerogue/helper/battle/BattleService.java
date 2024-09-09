package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.pokemon2.repository.Pokemon2Repository;
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
    private final Pokemon2Repository pokemon2Repository;
    private final TypeMatchingRepository typeMatchingRepository;

    private Map<Integer, List<MoveResponse>> findByDexnumberCache = new HashMap<>();

    public List<WeatherResponse> findWeathers() {
        return Arrays.stream(Weather.values())
                .map(WeatherResponse::from)
                .toList();
    }

    public List<MoveResponse> findMovesByPokemon(String pokemonId) {
        List<String> allMoveIds = new ArrayList<>();
        Pokemon pokemon = pokemon2Repository.findById(pokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));

        List<String> moves = new ArrayList<>();
        for (int i = 0; i < pokemon.moves().size(); i++) {
            if (i % 2 == 0) {
                moves.add(pokemon.moves().get(i));
            }
        }
        allMoveIds.addAll(moves);
        allMoveIds.addAll(pokemon.tmsMoves());
        allMoveIds.addAll(pokemon.eggMoves());
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
        for (Pokemon pokemon : pokemon2Repository.findAll().values()) {
            int pokemonId = Integer.parseInt(pokemon.speciesId());
            if (!findByDexnumberCache.containsKey(pokemonId)) {
                findByDexnumberCache.put(pokemonId, makeMoveResponse(pokemon));
            }
        }
    }

    private List<MoveResponse> makeMoveResponse(Pokemon pokemon) {
        List<String> allMoveIds = new ArrayList<>();
        List<String> moves = new ArrayList<>();
        for (int i = 0; i < pokemon.moves().size(); i++) {
            if (i % 2 == 0) {
                moves.add(pokemon.moves().get(i));
            }
        }
        allMoveIds.addAll(moves);
        allMoveIds.addAll(pokemon.tmsMoves());
        allMoveIds.addAll(pokemon.eggMoves());
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
        Pokemon myPokemon = pokemon2Repository.findById(myPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        Pokemon rivalPokemon = pokemon2Repository.findById(rivalPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        BattleMove move = battleMoveRepository.findById(myMoveId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));
        Type moveType = move.type();

        double finalAccuracy = calculateAccuracy(move, weather);
        double totalMultiplier = getTotalMultiplier(move, weather, rivalPokemon, myPokemon);

        return new BattleResultResponse(
                move.power(),
                totalMultiplier,
                finalAccuracy,
                move.name(),
                move.effect(),
                moveType.getName(),
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
            Pokemon rivalPokemon,
            Pokemon myPokemon) {
        if (!move.isAttackMove()) {
            return 1;
        }
        Type moveType = move.type();
        double weatherMultiplier = getWeatherMultiplier(moveType, weather);
        System.out.println(rivalPokemon.type1() + " : " + rivalPokemon.type2());
        List<Type> types = new ArrayList<>();
        if (!rivalPokemon.type1().isEmpty()) {
            types.add(Type.findByEngName(rivalPokemon.type1()).orElseThrow());
        }
        if (!rivalPokemon.type2().isEmpty()) {
            types.add(Type.findByEngName(rivalPokemon.type2()).orElseThrow());
        }
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
                .map(toType -> typeMatchingRepository.findByFromTypeAndToType(moveType, toType)
                        .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_MATCHING_ERROR)))
                .map(TypeMatching::result)
                .reduce(1d, (a, b) -> a * b);
    }

    private double getSameTypeAttackBonusMultiplier(Type moveType, Pokemon rivalPokemon) {
        if (rivalPokemon.hasSameType(moveType)) {
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
