package com.pokerogue.helper.battle;

import java.util.List;

public record WeatherResponse(
        String id,
        String name,
        String description,
        List<String> effects
) {

    public static WeatherResponse from(Weather weather) {
        return new WeatherResponse(weather.getId(), weather.getName(), weather.getDescription(), weather.getEffects());
    }
}
