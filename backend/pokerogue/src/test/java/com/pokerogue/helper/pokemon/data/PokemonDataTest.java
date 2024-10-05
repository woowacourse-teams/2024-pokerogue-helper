package com.pokerogue.helper.pokemon.data;

import static com.pokerogue.helper.pokemon.data.PokemonValidator.validatePokemonIdFormat;
import static com.pokerogue.helper.pokemon.data.PokemonValidator.validatePokemonSize;

import com.pokerogue.environment.repository.RepositoryTest;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PokemonDataTest extends RepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @DisplayName("포켓몬 데이터의 개수를 확인한다.")
    @Test
    void pokemonCount() {
        List<Pokemon> pokemons = pokemonRepository.findAll();

        ThrowingCallable callable = () -> validatePokemonSize(pokemons);

        Assertions.assertThatCode(callable).doesNotThrowAnyException();
    }

    @DisplayName("포켓몬 데이터의 아이디 형식을 확인한다.")
    @Test
    @Disabled
    void pokemonIdFormat() {
        List<Pokemon> pokemons = pokemonRepository.findAll();

        ThrowingCallable callable = () -> validatePokemonIdFormat(pokemons);

        Assertions.assertThatCode(callable).doesNotThrowAnyException();
    }

    @DisplayName("포켓몬 데이터의 종족값은 ")
    @Test
    @Disabled
    void pokemonIdFormat() {
        List<Pokemon> pokemons = pokemonRepository.findAll();

        ThrowingCallable callable = () -> validatePokemonIdFormat(pokemons);

        Assertions.assertThatCode(callable).doesNotThrowAnyException();
    }
}
