package com.pokerogue.helper.battle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

class DataInitializerTest {

    @Test
    @DisplayName("날씨, 기술 데이터를 세팅한다.")
    void setWeathersData() {
        WeatherRepository weatherRepository = new WeatherRepository();
        MoveRepository moveRepository = new MoveRepository();
        PokemonMovesByMachineRepository pokemonMovesByMachineRepository = new PokemonMovesByMachineRepository();
        PokemonMovesBySelfRepository pokemonMovesBySelfRepository = new PokemonMovesBySelfRepository();
        PokemonMovesByEggRepository pokemonMovesByEggRepository = new PokemonMovesByEggRepository();
        DataInitializer dataInitializer = new DataInitializer(
                weatherRepository,
                moveRepository,
                pokemonMovesByMachineRepository,
                pokemonMovesBySelfRepository,
                pokemonMovesByEggRepository
        );
        dataInitializer.run(new DefaultApplicationArguments());

        assertAll(() -> {
            assertThat(weatherRepository.findAll()).hasSize(10);
            assertThat(moveRepository.findAll()).hasSize(902);
            assertThat(pokemonMovesByMachineRepository.findAll()).hasSize(1082);
            assertThat(pokemonMovesBySelfRepository.findAll()).hasSize(1082);
            assertThat(pokemonMovesByEggRepository.findAll()).hasSize(1082);
        });
    }
}
