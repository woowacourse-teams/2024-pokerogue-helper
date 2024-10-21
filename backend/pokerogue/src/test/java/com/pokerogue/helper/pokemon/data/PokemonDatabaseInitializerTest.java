package com.pokerogue.helper.pokemon.data;

import com.pokerogue.helper.ability.repository.AbilityRepository;
import com.pokerogue.helper.pokemon.config.PokemonDatabaseInitializer;
import com.pokerogue.helper.pokemon.repository.EvolutionRepository;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.DefaultApplicationArguments;

class PokemonDatabaseInitializerTest {

    @Test
    @DisplayName("포켓몬 정보가 지정된 개수만큼 저장된다")
    void savePokemonCount() {
        PokemonRepository pokemonRepository = new PokemonRepository();
        PokemonDatabaseInitializer pokemonDatabaseInitializer = new PokemonDatabaseInitializer(
                pokemonRepository,
                new EvolutionRepository(),
                new AbilityRepository()
        );

        pokemonDatabaseInitializer.run(new DefaultApplicationArguments());

        Assertions.assertThat(pokemonRepository.findAll()).hasSize(1268);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bulbasaur", "chikorita", "wailmer", "virizion", "golisopod", "melmetal", "spidops",
            "hydrapple", "alola_exeggutor"})
    @DisplayName("포켓몬의 저장된 이름을 확인한다")
    void savePokemonNames(String name) {
        PokemonRepository pokemonRepository = new PokemonRepository();
        PokemonDatabaseInitializer pokemonDatabaseInitializer = new PokemonDatabaseInitializer(
                pokemonRepository,
                new EvolutionRepository(),
                new AbilityRepository()
        );

        pokemonDatabaseInitializer.run(new DefaultApplicationArguments());

        Assertions.assertThat(pokemonRepository.findById(name)).isNotNull();
    }
}
