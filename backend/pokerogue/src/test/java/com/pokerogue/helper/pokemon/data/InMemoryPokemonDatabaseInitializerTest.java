package com.pokerogue.helper.pokemon.data;

import com.pokerogue.helper.ability.repository.InMemoryAbilityRepository;
import com.pokerogue.helper.pokemon.config.PokemonDatabaseInitializer;
import com.pokerogue.helper.pokemon.repository.EvolutionRepository;
import com.pokerogue.helper.pokemon.repository.InMemoryPokemonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.DefaultApplicationArguments;

class InMemoryPokemonDatabaseInitializerTest {

    @Test
    @DisplayName("포켓몬 정보가 지정된 개수만큼 저장된다")
    void savePokemonCount() {
        InMemoryPokemonRepository inMemoryPokemonRepository = new InMemoryPokemonRepository();
        PokemonDatabaseInitializer pokemonDatabaseInitializer = new PokemonDatabaseInitializer(
                inMemoryPokemonRepository,
                new EvolutionRepository(),
                new InMemoryAbilityRepository()
        );

        pokemonDatabaseInitializer.run(new DefaultApplicationArguments());

        Assertions.assertThat(inMemoryPokemonRepository.findAll()).hasSize(1268);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bulbasaur", "chikorita", "wailmer", "virizion", "golisopod", "melmetal", "spidops",
            "hydrapple", "alola_exeggutor"})
    @DisplayName("포켓몬의 저장된 이름을 확인한다")
    void savePokemonNames(String name) {
        InMemoryPokemonRepository inMemoryPokemonRepository = new InMemoryPokemonRepository();
        PokemonDatabaseInitializer pokemonDatabaseInitializer = new PokemonDatabaseInitializer(
                inMemoryPokemonRepository,
                new EvolutionRepository(),
                new InMemoryAbilityRepository()
        );

        pokemonDatabaseInitializer.run(new DefaultApplicationArguments());

        Assertions.assertThat(inMemoryPokemonRepository.findById(name)).isNotNull();
    }
}
