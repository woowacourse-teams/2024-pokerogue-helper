package com.pokerogue.helper.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class WeatherRepository {

    private final Map<String, Weather> weathers = new HashMap<>();

    public void save(Weather weather) {
        weathers.put(weather.id(), weather);
    }

    public List<Weather> findAll() {
        return weathers.values()
                .stream()
                .toList();
    }
}
