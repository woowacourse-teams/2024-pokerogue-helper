package com.pokerogue.helper.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
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

    public Optional<Weather> findById(String id) {
        return Optional.ofNullable(weathers.get(id));
    }
}
