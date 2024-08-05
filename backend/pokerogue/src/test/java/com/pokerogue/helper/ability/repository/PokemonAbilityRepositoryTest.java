package com.pokerogue.helper.ability.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.environ.repository.RepositoryTest;
import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PokemonAbilityRepositoryTest extends RepositoryTest {

    @Autowired
    private PokemonAbilityRepository pokemonAbilityRepository;

    @Test
    @DisplayName("특성에 해당하는 포켓몬과 포켓몬 타입들을 조회한다.")
    void findByIdWithPokemonAndPokemonTypes() {
        PokemonAbility pokemonAbility = pokemonAbilityRepository.findAll().get(0);
        Long pokemonAbilityId = pokemonAbility.getId();

        PokemonAbility queriedPokemon = pokemonAbilityRepository.findByIdWithPokemonAndPokemonTypes(pokemonAbilityId)
                .get();
        Set<PokemonAbilityMapping> pokemonAbilityMappings = queriedPokemon.getPokemonAbilityMappings();
        List<Pokemon> pokemons = pokemonAbilityMappings.stream()
                .map(PokemonAbilityMapping::getPokemon)
                .toList();

        assertAll(
                () -> assertThat(pokemons).isNotEmpty(),
                () -> assertThat(pokemons.stream()
                        .flatMap(pokemon -> pokemon.getPokemonTypeMappings().stream()
                                .map(PokemonTypeMapping::getPokemonType))
                        .toList()).isNotEmpty()
        );
    }
}
