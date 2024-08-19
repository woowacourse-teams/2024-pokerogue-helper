package com.pokerogue.helper.battle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.client.FakeS3ImageClient;
import com.pokerogue.external.s3.service.S3Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

class DataInitializerTest {

    @Test
    @DisplayName("날씨, 기술 데이터를 세팅한다.")
    void setWeathersData() {
        WeatherRepository weatherRepository = new WeatherRepository();
        BattleMoveRepository battleMoveRepository = new BattleMoveRepository();
        PokemonMovesByMachineRepository pokemonMovesByMachineRepository = new PokemonMovesByMachineRepository();
        PokemonMovesBySelfRepository pokemonMovesBySelfRepository = new PokemonMovesBySelfRepository();
        PokemonMovesByEggRepository pokemonMovesByEggRepository = new PokemonMovesByEggRepository();
        BattlePokemonTypeRepository battlePokemonTypeRepository = new BattlePokemonTypeRepository();
        S3Service s3Service = new S3Service(new FakeS3ImageClient());
        DataInitializer dataInitializer = new DataInitializer(
                weatherRepository,
                battleMoveRepository,
                pokemonMovesByMachineRepository,
                pokemonMovesBySelfRepository,
                pokemonMovesByEggRepository,
                battlePokemonTypeRepository,
                s3Service
        );
        dataInitializer.run(new DefaultApplicationArguments());

        assertAll(() -> {
            assertThat(weatherRepository.findAll()).hasSize(10);
            assertThat(battleMoveRepository.findAll()).hasSize(902);
            assertThat(pokemonMovesByMachineRepository.findAll()).hasSize(1082);
            assertThat(pokemonMovesBySelfRepository.findAll()).hasSize(1082);
            assertThat(pokemonMovesByEggRepository.findAll()).hasSize(1082);
            assertThat(battlePokemonTypeRepository.findAll()).hasSize(20);
        });
    }
}
