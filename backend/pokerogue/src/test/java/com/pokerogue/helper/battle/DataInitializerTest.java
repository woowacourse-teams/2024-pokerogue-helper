package com.pokerogue.helper.battle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

class DataInitializerTest {

    @Test
    @DisplayName("날씨 데이터를 세팅한다.")
    void setWeathersData() {
        WeatherRepository weatherRepository = new WeatherRepository();
        DataInitializer dataInitializer = new DataInitializer(weatherRepository);
        dataInitializer.run(new DefaultApplicationArguments());

        assertThat(weatherRepository.findAll()).hasSize(10);
    }
}
