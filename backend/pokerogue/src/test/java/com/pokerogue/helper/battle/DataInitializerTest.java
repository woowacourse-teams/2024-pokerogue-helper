package com.pokerogue.helper.battle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

class DataInitializerTest {

    @Test
    @DisplayName("날씨 데이터를 세팅한다.")
    void setWeathersData() {
        WeatherRepository weatherRepository = new WeatherRepository();
        MoveRepository moveRepository = new MoveRepository();
        DataInitializer dataInitializer = new DataInitializer(weatherRepository, moveRepository);
        dataInitializer.run(new DefaultApplicationArguments());

        assertAll(() -> {
            assertThat(weatherRepository.findAll()).hasSize(10);
            assertThat(moveRepository.findAll()).hasSize(902);
        });
    }
}
