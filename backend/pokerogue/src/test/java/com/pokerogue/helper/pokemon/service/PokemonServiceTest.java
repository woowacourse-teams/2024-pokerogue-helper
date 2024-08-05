package com.pokerogue.helper.pokemon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.dto.PokedexResponse;
import com.pokerogue.helper.pokemon.dto.PokemonResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PokemonServiceTest extends ServiceTest {

    @Autowired
    private PokemonService pokemonService;

    @Test
    @DisplayName("모든 포켓몬 목록을 조회한다.")
    void findPokemons() {
        List<PokemonResponse> pokemons = pokemonService.findPokemons();

        assertThat(pokemons).isNotEmpty();
    }

    @Test
    @DisplayName("한 포켓몬의 도감 상세 정보를 조회한다.")
    void findPokedexDetails() {
        long pokemonId = pokemonService.findPokemons().get(0).id();

        PokedexResponse pokedexDetails = pokemonService.findPokedexDetails(pokemonId);

        assertAll(() -> {
            assertThat(pokedexDetails.pokedexNumber()).isNotNull();
            assertThat(pokedexDetails.pokemonTypeResponses()).isNotEmpty();
            assertThat(pokedexDetails.pokemonAbilityResponses()).isNotEmpty();
        });
    }

    @Test
    @DisplayName("존재하지 않는 포켓몬의 상세 정보를 조회하면 예외가 발생한다.")
    void findPokedexDetailsWhenNotExist() {
        assertThatThrownBy(() -> pokemonService.findPokedexDetails(Long.MAX_VALUE))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.POKEMON_NOT_FOUND.getMessage());
    }
}
