package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.environment.repository.RepositoryTest;
import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import com.pokerogue.helper.type.domain.PokemonType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PokemonRepositoryTest extends RepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    @DisplayName("모든 포켓몬을 타입 정보와 함께 조회한다.")
    void findAllWithTypes() {
        List<Pokemon> pokemons = pokemonRepository.findAllWithTypes();

        List<PokemonType> pokemonTypes = selectedPokemonTypes(pokemons);
        assertAll(() -> {
            assertThat(pokemons).isNotEmpty();
            assertThat(pokemonTypes).isNotEmpty();
        });
    }

    private List<PokemonType> selectedPokemonTypes(List<Pokemon> pokemons) {
        return pokemons.stream()
                .map(Pokemon::getPokemonTypeMappings)
                .flatMap(pokemonTypeMappings -> pokemonTypeMappings.stream()
                        .map(PokemonTypeMapping::getPokemonType))
                .toList();
    }

    @Test
    @DisplayName("모든 포켓몬을 id 순으로 조회한다.")
    void findAllWithTypesOrderById() {
        List<Pokemon> pokemons = pokemonRepository.findAllWithTypes();

        List<Long> pokemonIds = pokemons.stream()
                .map(Pokemon::getId)
                .toList();
        assertThat(pokemonIds).isSortedAccordingTo(Long::compare);
    }

    @Test
    @DisplayName("id로 포켓몬 상세 정보를 타입, 특성 정보와 함께 조회한다.")
    void findDetailsById() {
        Pokemon pokemon = pokemonRepository.findAllWithTypes().get(0);
        Long firstPokemonId = pokemon.getId();

        Pokemon queriedPokemon = pokemonRepository.findDetailsById(firstPokemonId).get();

        assertAll(() -> {
            assertThat(queriedPokemon.getPokemonAbilityMappings().stream()
                    .map(PokemonAbilityMapping::getPokemonAbility).toList()).isNotEmpty();
            assertThat(queriedPokemon.getPokemonTypeMappings().stream()
                    .map(PokemonTypeMapping::getPokemonType).toList()).isNotEmpty();
        });
    }
}
