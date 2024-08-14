package com.pokerogue.helper.battle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    private static final int FIRST_LINE = 3;
    private static final String FIELD_DELIMITER = "/";
    private static final String LIST_DELIMITER = ",";

    private final WeatherRepository weatherRepository;
    private final MoveRepository moveRepository;

    @Override
    public void run(ApplicationArguments args) {
        String weatherFilePath = "src/main/java/com/pokerogue/helper/battle/data/weather.txt";
        String moveFilePath = "src/main/java/com/pokerogue/helper/battle/data/move.txt";

        saveData(weatherFilePath, fields -> {
            Weather weather = createWeather(fields);
            weatherRepository.save(weather);
        });
        saveData(moveFilePath, fields -> {
            Move move = createMove(fields);
            moveRepository.save(move);
        });
    }

    private void saveData(String path, Consumer<List<String>> createAndSaveOperation) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int lineCount = 0;
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount < FIRST_LINE) {
                    continue;
                }
                List<String> fields = splitFields(line);
                createAndSaveOperation.accept(fields);
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getStackTrace()[0]);
        }
    }

    private List<String> splitFields(String line) {
        return Arrays.stream(line.split(FIELD_DELIMITER))
                .map(String::trim)
                .toList();
    }

    private Weather createWeather(List<String> fields) {
        String id = createId(fields.get(0));
        String name = fields.get(0);
        String description = fields.get(1);
        List<String> effects = Arrays.stream(fields.get(2).split(LIST_DELIMITER))
                .map(String::trim)
                .toList();
        return new Weather(id, name, description, effects);
    }

    private Move createMove(List<String> fields) {
        return new Move(
                createId(fields.get(1)) + fields.get(0),
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

    private Integer convertToInteger(String data) {
        try {
            return Integer.valueOf(data);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String createId(String data) {
        return data.replace(' ', '-');
    }
}
