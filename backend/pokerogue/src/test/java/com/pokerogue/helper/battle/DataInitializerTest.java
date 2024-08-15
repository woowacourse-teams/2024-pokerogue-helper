package com.pokerogue.helper.battle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

class DataInitializerTest {

    @Test
    @DisplayName("날씨와 기술 데이터를 세팅한다.")
    void setWeathersData() {
        WeatherRepository weatherRepository = new WeatherRepository();
        MoveRepository moveRepository = new MoveRepository();
        BattlePokemonRepository battlePokemonRepository = new BattlePokemonRepository();
        PokemonMovesByMachineRepository pokemonMovesByMachineRepository = new PokemonMovesByMachineRepository();
        DataInitializer dataInitializer = new DataInitializer(
                weatherRepository,
                moveRepository,
                battlePokemonRepository,
                pokemonMovesByMachineRepository);
        dataInitializer.run(new DefaultApplicationArguments());

        assertAll(() -> {
            assertThat(weatherRepository.findAll()).hasSize(10);
            assertThat(moveRepository.findAll()).hasSize(902);
            /*
            현재 이름 중복되는 포켓몬 때문에 개수 확인을 하지 않았음
             */
            assertThat(pokemonMovesByMachineRepository.findAll()).isNotEmpty();
            assertThat(battlePokemonRepository.findAll()).isNotEmpty();
        });
    }
}
