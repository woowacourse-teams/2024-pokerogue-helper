package com.pokerogue.helper.battle.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.battle.data.Weather;
import com.pokerogue.helper.battle.dto.WeatherResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class WeatherServiceTest extends ServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    @DisplayName("모든 날씨 목록을 조회한다.")
    void findWeathers() {
        List<WeatherResponse> weatherResponses = weatherService.findWeathers();

        List<Weather> allWeathers = Arrays.stream(Weather.values())
                .toList();
        assertThat(weatherResponses).hasSize(allWeathers.size());
    }
}
