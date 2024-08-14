package com.pokerogue.helper.battle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

    @Override
    public void run(ApplicationArguments args) {
        String weatherFilePath = "src/main/java/com/pokerogue/helper/battle/data/weather.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(weatherFilePath))) {
            int lineCount = 0;
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount < FIRST_LINE) {
                    continue;
                }
                Weather weather = createWeather(line);
                weatherRepository.save(weather);
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getStackTrace()[0]);
        }
    }

    private Weather createWeather(String data) {
        List<String> fields = Arrays.stream(data.split(FIELD_DELIMITER))
                .map(String::trim)
                .toList();
        String id = createId(fields.get(0));
        String name = fields.get(0);
        String description = fields.get(1);
        List<String> effects = Arrays.stream(fields.get(2).split(LIST_DELIMITER))
                .map(String::trim)
                .toList();
        return new Weather(id, name, description, effects);
    }

    private String createId(String data) {
        return data.replace(' ', '-');
    }
}
