package com.pokerogue.helper.battle;

import com.pokerogue.external.s3.service.S3Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    private final WeatherRepository weatherRepository;
    private final MoveRepository moveRepository;
    private final PokemonMovesByMachineRepository pokemonMovesByMachineRepository;
    private final PokemonMovesBySelfRepository pokemonMovesBySelfRepository;
    private final PokemonMovesByEggRepository pokemonMovesByEggRepository;
    private final BattlePokemonTypeRepository battlePokemonTypeRepository;
    private final S3Service s3Service;

    @Override
    public void run(ApplicationArguments args) {
        saveData("weather.txt", fields -> {
            Weather weather = createWeather(fields);
            weatherRepository.save(weather);
        });
        saveData("battle-move.txt", fields -> {
            Move move = createMove(fields);
            moveRepository.save(move);
        });
        saveData("tms.txt", fields -> {
            PokemonMovesByMachine pokemonMovesByMachine = createPokemonMovesByMachine(fields);
            pokemonMovesByMachineRepository.save(pokemonMovesByMachine);
        });
        saveData("battle-pokemon.txt", fields -> {
            PokemonMovesBySelf pokemonMovesBySelf = createPokemonMovesBySelf(fields);
            pokemonMovesBySelfRepository.save(pokemonMovesBySelf);
            PokemonMovesByEgg pokemonMovesByEgg = createPokemonMovesByEgg(fields);
            pokemonMovesByEggRepository.save(pokemonMovesByEgg);
        });
        saveData("type.txt", fields -> {
            PokemonType pokemonType = createPokemonType(fields);
            battlePokemonTypeRepository.save(pokemonType);
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
                .toList();
    }

    private Weather createWeather(List<String> fields) {
        String id = fields.get(0);
        String name = fields.get(1);
        String description = fields.get(2);
        List<String> effects = Arrays.stream(fields.get(3).split(LIST_DELIMITER))
                .map(String::trim)
                .toList();

        return new Weather(id, name, description, effects);
    }

    private Move createMove(List<String> fields) {
        return new Move(
                fields.get(1), /* 우선은 한글 이름을 id로 설정, 추후 숫자로 변경 */
                fields.get(1),
                fields.get(2),
                fields.get(3),
                fields.get(4),
                fields.get(5),
                fields.get(6),
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
        List<String> moveIds = Arrays.stream(fields.get(19).split(LIST_DELIMITER))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        return new PokemonMovesBySelf(pokedexNumber, moveIds);
    }

    private PokemonMovesByEgg createPokemonMovesByEgg(List<String> fields) {
        Integer pokedexNumber = convertToInteger(fields.get(0));
        List<String> moveIds = Arrays.stream(fields.get(18).split(LIST_DELIMITER))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        return new PokemonMovesByEgg(pokedexNumber, moveIds);
    }

    private PokemonType createPokemonType(List<String> fields) {
        String name = fields.get(0);
        String engName = fields.get(1);
        String image = s3Service.getPokerogueTypeImageFromS3(engName);

        return new PokemonType(name, engName, image);
    }


    private Integer convertToInteger(String data) {
        try {
            return Integer.valueOf(data);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
