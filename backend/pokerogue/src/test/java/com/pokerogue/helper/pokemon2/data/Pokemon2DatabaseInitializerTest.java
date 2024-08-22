package com.pokerogue.helper.pokemon2.data;

import com.pokerogue.helper.pokemon2.config.Pokemon2DatabaseInitializer;
import com.pokerogue.helper.pokemon2.repository.EvolutionRepository;
import com.pokerogue.helper.pokemon2.repository.MoveRepository;
import com.pokerogue.helper.pokemon2.repository.Pokemon2Repository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.DefaultApplicationArguments;

class Pokemon2DatabaseInitializerTest {

    @Test
    @DisplayName("포켓몬 정보가 지정된 개수만큼 저장된다")
    void savePokemonCount() {
        Pokemon2Repository pokemon2Repository = new Pokemon2Repository();
        Pokemon2DatabaseInitializer pokemon2DatabaseInitializer = new Pokemon2DatabaseInitializer(
                pokemon2Repository,
                new MoveRepository(),
                new EvolutionRepository()
        );

        pokemon2DatabaseInitializer.run(new DefaultApplicationArguments());

        Assertions.assertThat(pokemon2Repository.findAll()).hasSize(1268);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bulbasaur", "chikorita", "wailmer", "virizion", "golisopod", "melmetal", "spidops",
            "hydrapple", "alola_exeggutor"})
    @DisplayName("포켓몬의 저장된 이름을 확인한다")
    void savePokemonNames(String name) {
        Pokemon2Repository pokemon2Repository = new Pokemon2Repository();
        Pokemon2DatabaseInitializer pokemon2DatabaseInitializer = new Pokemon2DatabaseInitializer(
                pokemon2Repository,
                new MoveRepository(),
                new EvolutionRepository()
        );

        pokemon2DatabaseInitializer.run(new DefaultApplicationArguments());

        Assertions.assertThat(pokemon2Repository.findById(name)).isNotNull();
    }
}
