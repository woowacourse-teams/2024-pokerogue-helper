package com.pokerogue.helper.pokemon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.type.domain.PokemonType;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PokemonTest {

    @Test
    @DisplayName("포켓몬의 타입 목록을 id 순으로 조회한다.")
    void getSortedPokemonTypes() {
        List<PokemonType> pokemonTypes = List.of(
                new PokemonType(2L, "grass", "풀", "grass dummy image"),
                new PokemonType(1L, "poison", "독", "poison dummy image")
        );
        List<PokemonTypeMapping> pokemonTypeMappings = pokemonTypes.stream()
                .map(type -> new PokemonTypeMapping(null, type))
                .toList();
        Pokemon pokemon = new Pokemon(1L, 1L, "이상해씨", "이상해씨", 6.9, 0.7, 45, 45, 55, 50, 60, 70, 500,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", pokemonTypeMappings,
                List.of());

        List<PokemonType> sortedPokemonTypes = pokemon.getSortedPokemonTypes();

        assertThat(sortedPokemonTypes).isSortedAccordingTo(Comparator.comparingLong(PokemonType::getId));
    }
}
