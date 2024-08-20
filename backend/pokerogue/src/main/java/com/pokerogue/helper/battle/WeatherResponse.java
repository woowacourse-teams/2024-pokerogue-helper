package com.pokerogue.helper.battle;

public record WeatherResponse(String id, String name) {

    public static WeatherResponse from(Weather weather) {
        return new WeatherResponse(weather.getId(), weather.name());
    }
}
