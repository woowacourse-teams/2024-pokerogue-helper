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
        BattleMoveRepository battleMoveRepository = new BattleMoveRepository();
        PokemonMovesByMachineRepository pokemonMovesByMachineRepository = new PokemonMovesByMachineRepository();
        PokemonMovesBySelfRepository pokemonMovesBySelfRepository = new PokemonMovesBySelfRepository();
        PokemonMovesByEggRepository pokemonMovesByEggRepository = new PokemonMovesByEggRepository();
        BattlePokemonRepository battlePokemonRepository = new BattlePokemonRepository();
        TypeMatchingRepository typeMatchingRepository = new TypeMatchingRepository();
        DataInitializer dataInitializer = new DataInitializer(
                battleMoveRepository,
                pokemonMovesByMachineRepository,
                pokemonMovesBySelfRepository,
                pokemonMovesByEggRepository,
                battlePokemonRepository,
                typeMatchingRepository
        );
        dataInitializer.run(new DefaultApplicationArguments());

        assertAll(() -> {
            assertThat(battleMoveRepository.findAll()).hasSize(920);
            assertThat(pokemonMovesByMachineRepository.findAll()).hasSize(1082);
            assertThat(pokemonMovesBySelfRepository.findAll()).hasSize(1082);
            assertThat(pokemonMovesByEggRepository.findAll()).hasSize(1082);
            assertThat(battlePokemonRepository.findAll()).hasSize(1350);
            assertThat(typeMatchingRepository.findAll()).hasSize(361);
        });
    }
}
