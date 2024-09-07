package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private static final int FIRST_LINE_NUMBER = 3;
    private static final String FIELD_DELIMITER = "/";
    private static final String LIST_DELIMITER = ",";

    private final BattleMoveRepository battleMoveRepository;
    private final PokemonMovesByMachineRepository pokemonMovesByMachineRepository;
    private final PokemonMovesBySelfRepository pokemonMovesBySelfRepository;
    private final PokemonMovesByEggRepository pokemonMovesByEggRepository;
    private final BattlePokemonRepository battlePokemonRepository;
    private final TypeMatchingRepository typeMatchingRepository;

    @Override
    public void run(ApplicationArguments args) {
        saveData("data/battle/battle-move.txt", fields -> {
            BattleMove battleMove = createMove(fields);
            battleMoveRepository.save(battleMove);
        });
        saveData("data/battle/tms.txt", fields -> {
            PokemonMovesByMachine pokemonMovesByMachine = createPokemonMovesByMachine(fields);
            pokemonMovesByMachineRepository.save(pokemonMovesByMachine);
        });
        saveData("data/battle/battle-pokemon.txt", fields -> {
            PokemonMovesBySelf pokemonMovesBySelf = createPokemonMovesBySelf(fields);
            pokemonMovesBySelfRepository.save(pokemonMovesBySelf);
            PokemonMovesByEgg pokemonMovesByEgg = createPokemonMovesByEgg(fields);
            pokemonMovesByEggRepository.save(pokemonMovesByEgg);
            BattlePokemon battlePokemon = createBattlePokemon(fields);
            battlePokemonRepository.save(battlePokemon);
        });
        saveData("data/battle/type-matching.txt", fields -> {
            TypeMatching typeMatching = createTypeMatching(fields);
            typeMatchingRepository.save(typeMatching);
        });
    }

    private void saveData(String path, Consumer<List<String>> createAndSaveOperation) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            int lineCount = 0;
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount < FIRST_LINE_NUMBER) {
                    continue;
                }
                List<String> fields = splitFields(line);
                createAndSaveOperation.accept(fields);
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getMessage(), e);
        }
    }

    private List<String> splitFields(String line) {
        return Arrays.stream(line.split(FIELD_DELIMITER))
                .map(String::trim)
                .map(this::regularizeEmptyField)
                .toList();
    }

    private String regularizeEmptyField(String field) {
        if (field.equals("EMPTY")) {
            return "";
        }
        return field;
    }

    private BattleMove createMove(List<String> fields) {
        Type moveType = Type.findByName(fields.get(4))
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
        MoveCategory moveCategory = MoveCategory.findByEngName(fields.get(6).toLowerCase())
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));

        return new BattleMove(
                fields.get(0),
                fields.get(1),
                fields.get(2),
                fields.get(3),
                moveType,
                fields.get(5),
                moveCategory,
                fields.get(7),
                convertToInteger(fields.get(8)),
                convertToInteger(fields.get(9)),
                convertToInteger(fields.get(10)),
                convertToInteger(fields.get(11)),
                convertToInteger(fields.get(12)),
                convertToInteger(fields.get(13)),
                fields.get(14)
        );
    }

    private PokemonMovesByMachine createPokemonMovesByMachine(List<String> fields) {
        Integer pokedexNumber = convertToInteger(fields.get(0));
        List<String> moveIds = Arrays.stream(fields.get(2).split(LIST_DELIMITER))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        return new PokemonMovesByMachine(pokedexNumber, moveIds);
    }

    private PokemonMovesBySelf createPokemonMovesBySelf(List<String> fields) {
        Integer pokedexNumber = convertToInteger(fields.get(0));
        List<String> moveIds = Arrays.stream(fields.get(5).split(LIST_DELIMITER))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        return new PokemonMovesBySelf(pokedexNumber, moveIds);
    }

    private PokemonMovesByEgg createPokemonMovesByEgg(List<String> fields) {
        Integer pokedexNumber = convertToInteger(fields.get(0));
        List<String> moveIds = Arrays.stream(fields.get(4).split(LIST_DELIMITER))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        return new PokemonMovesByEgg(pokedexNumber, moveIds);
    }

    private BattlePokemon createBattlePokemon(List<String> fields) {
        String id = fields.get(1);
        List<Type> types = new ArrayList<>();
        if (existTypeName(fields.get(2))) {
            Type type = Type.findByName(fields.get(2))
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
            types.add(type);
        }
        if (existTypeName(fields.get(3))) {
            Type type = Type.findByName(fields.get(3))
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
            types.add(type);
        }

        return new BattlePokemon(id, types);
    }

    private TypeMatching createTypeMatching(List<String> fields) {
        Type fromType = Type.findByEngName(fields.get(0))
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
        Type toType = Type.findByEngName(fields.get(1))
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
        double result = convertToDouble(fields.get(2));

        return new TypeMatching(fromType, toType, result);
    }

    private boolean existTypeName(String data) {
        return !data.equals("Type.undefined");
    }

    private double convertToDouble(String data) {
        try {
            return Double.parseDouble(data);
        } catch (NumberFormatException e) {
            throw new GlobalCustomException(ErrorMessage.PARSE_ERROR);
        }
    }

    private Integer convertToInteger(String data) {
        try {
            return Integer.valueOf(data);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
