package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final BattleMoveRepository battleMoveRepository;
    private final PokemonMovesByEggRepository pokemonMovesByEggRepository;
    private final PokemonMovesBySelfRepository pokemonMovesBySelfRepository;
    private final PokemonMovesByMachineRepository pokemonMovesByMachineRepository;
    private final BattlePokemonRepository battlePokemonRepository;
    private final TypeMatchingRepository typeMatchingRepository;

    public List<WeatherResponse> findWeathers() {
        return Arrays.stream(Weather.values())
                .map(WeatherResponse::from)
                .toList();
    }

    public List<MoveResponse> findMovesByPokemon(Integer pokedexNumber) {
        List<String> allMoveIds = new ArrayList<>();
        PokemonMovesBySelf pokemonMovesBySelf = pokemonMovesBySelfRepository.findByPokedexNumber(pokedexNumber)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_BY_SELF_NOT_FOUND));
        PokemonMovesByMachine pokemonMovesByMachine = pokemonMovesByMachineRepository.findByPokedexNumber(pokedexNumber)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_BY_MACHINE_NOT_FOUND));
        PokemonMovesByEgg pokemonMovesByEgg = pokemonMovesByEggRepository.findByPokedexNumber(pokedexNumber)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_BY_EGG_NOT_FOUND));

        allMoveIds.addAll(pokemonMovesBySelf.moveIds());
        allMoveIds.addAll(pokemonMovesByMachine.moveIds());
        allMoveIds.addAll(pokemonMovesByEgg.moveIds());
        List<BattleMove> battleMoves = allMoveIds.stream()
                .map(this::findMoveById)
                .toList();

        return battleMoves.stream()
                .map(this::toMoveResponseWithLogo)
                .toList();
    }

    private BattleMove findMoveById(String id) {
        return battleMoveRepository.findById(id)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_NOT_FOUND));
    }

    private MoveResponse toMoveResponseWithLogo(BattleMove battleMove) {
        Type moveType = battleMove.type();
        String typeLogo = moveType.getImage();
        MoveCategory moveCategory = MoveCategory.findByName(battleMove.category().toLowerCase());
        String categoryLogo = moveCategory.getImage();

        return MoveResponse.of(battleMove, typeLogo, categoryLogo);
    }

    public BattleResultResponse calculateBattleResult(String weatherId, String myPokemonId, String rivalPokemonId,
                                                      String myMoveId) {
        Weather weather = Weather.findById(weatherId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.WEATHER_NOT_FOUND));
        BattlePokemon myPokemon = battlePokemonRepository.findById(myPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        BattlePokemon rivalPokemon = battlePokemonRepository.findById(rivalPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        BattleMove move = battleMoveRepository.findById(myMoveId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));
        Type moveType = move.type();

        double weatherMultiplier = getWeatherMultiplier(moveType, weather);
        double typeMatchingMultiplier = getTypeMatchingMultiplier(moveType, rivalPokemon.pokemonTypes());
        double sameTypeBonusMultiplier = getSameTypeAttackBonusMultiplier(moveType, myPokemon);
        double stringWindMultiplier = getStringWindMultiplier(moveType, rivalPokemon.pokemonTypes(), weather);

        double totalMultiplier =
                weatherMultiplier * typeMatchingMultiplier * sameTypeBonusMultiplier * stringWindMultiplier;
        double finalAccuracy = calculateAccuracy(move, weather);

        return new BattleResultResponse(
                move.power(),
                totalMultiplier,
                finalAccuracy,
                move.name(),
                move.effect(),
                moveType.getName(),
                move.category(),
                weather.getDescription(),
                weather.getEffects()
        );
    }

    private double getWeatherMultiplier(Type moveType, Weather weather) {
        if (weather == Weather.SUNNY || weather == Weather.HARSH_SUN) {
            if (moveType == Type.FIRE) {
                return 1.5;
            }
            if (moveType == Type.WATER) {
                return 0.5;
            }
        }
        if (weather == Weather.RAIN || weather == Weather.HEAVY_RAIN) {
            if (moveType == Type.FIRE) {
                return 0.5;
            }
            if (moveType == Type.WATER) {
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

    private double getSameTypeAttackBonusMultiplier(Type moveType, BattlePokemon myPokemon) {
        List<Type> rivalPokemonTypes = myPokemon.pokemonTypes();
        boolean hasSameType = rivalPokemonTypes.stream()
                .anyMatch(moveType::equals);
        if (hasSameType) {
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

    private double calculateAccuracy(BattleMove move, Weather weather) {
        if (weather == Weather.FOG) {
            return (double) move.accuracy() * 0.9;
        }
        return (double) move.accuracy();
    }
}
