package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final WeatherRepository weatherRepository;
    private final BattleMoveRepository battleMoveRepository;
    private final PokemonMovesByEggRepository pokemonMovesByEggRepository;
    private final PokemonMovesBySelfRepository pokemonMovesBySelfRepository;
    private final PokemonMovesByMachineRepository pokemonMovesByMachineRepository;
    private final BattlePokemonTypeRepository battlePokemonTypeRepository;
    private final BattlePokemonRepository battlePokemonRepository;
    private final TypeMatchingRepository typeMatchingRepository;

    public List<WeatherResponse> findWeathers() {
        return weatherRepository.findAll().stream()
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
        PokemonType moveType = battlePokemonTypeRepository.findByName(battleMove.type())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
        String typeLogo = moveType.image();
        MoveCategory moveCategory = MoveCategory.findByName(battleMove.category().toLowerCase());
        String categoryLogo = moveCategory.getImage();

        return MoveResponse.of(battleMove, typeLogo, categoryLogo);
    }

    public BattleResultResponse calculateBattleResult(String weatherId, String myPokemonId, String rivalPokemonId,
                                                      String myMoveId) {
        Weather weather = weatherRepository.findById(weatherId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.WEATHER_NOT_FOUND));
        BattlePokemon myPokemon = battlePokemonRepository.findById(myPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        BattlePokemon rivalPokemon = battlePokemonRepository.findById(rivalPokemonId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));
        BattleMove move = battleMoveRepository.findById(myMoveId)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));
        PokemonType moveType = battlePokemonTypeRepository.findByName(move.type())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));

        double weatherMultiplier = getWeatherMultiplier(moveType, weather);
        double typeMatchingMultiplier = getTypeMatchingMultiplier(moveType, rivalPokemon.pokemonTypes());
        double sameTypeBonusMultiplier = getSameTypeAttackBonusMultiplier(moveType, myPokemon);

        // Todo: 강풍 고려
        double totalMultiplier = weatherMultiplier * typeMatchingMultiplier * sameTypeBonusMultiplier;
        double finalAccuracy = calculateAccuracy(move, weather);
        return new BattleResultResponse(
                move.power(),
                totalMultiplier,
                finalAccuracy,
                move.name(),
                move.effect(),
                moveType.name(),
                move.category(),
                weather.description(),
                weather.effects()
        );
    }

    private double getWeatherMultiplier(PokemonType moveType, Weather weather) {
        String weatherName = weather.name();
        String moveTypeName = moveType.name();
        if (weatherName.equals("쾌청") || weatherName.equals("강한 쾌청")) {
            if (moveTypeName.equals("불꽃")) {
                return 1.5;
            }
            if (moveTypeName.equals("물")) {
                return 0.5;
            }
            return 1;
        }
        if (weatherName.equals("비") || weatherName.equals("강한 비")) {
            if (moveTypeName.equals("불꽃")) {
                return 0.5;
            }
            if (moveTypeName.equals("물")) {
                return 1.5;
            }
            return 1;
        }
        return 1;
    }

    private double getTypeMatchingMultiplier(PokemonType moveType, List<PokemonType> defensivePokemonTypes) {
        String fromType = moveType.engName();
        return defensivePokemonTypes.stream()
                .map(PokemonType::engName)
                .map(toType -> typeMatchingRepository.findByFromTypeAndToType(fromType, toType).orElseThrow(
                        () -> new GlobalCustomException(ErrorMessage.TYPE_MATCHING_ERROR)))
                .map(TypeMatching::result)
                .reduce(1d, (a, b) -> a * b);
    }

    private double getSameTypeAttackBonusMultiplier(PokemonType moveType, BattlePokemon myPokemon) {
        List<PokemonType> rivalPokemonTypes = myPokemon.pokemonTypes();
        boolean hasSameType = rivalPokemonTypes.stream()
                .anyMatch(moveType::equals);
        if (hasSameType) {
            return 1.5;
        }
        return 1;
    }

    private double calculateAccuracy(BattleMove move, Weather weather) {
        if (weather.name().equals("안개")) {
            return (double) move.accuracy() * 0.9;
        }
        return (double) move.accuracy();
    }
}
