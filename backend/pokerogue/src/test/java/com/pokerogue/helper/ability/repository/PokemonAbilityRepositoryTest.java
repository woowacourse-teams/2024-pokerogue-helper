package com.pokerogue.helper.ability.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.repository.RepositoryTest;
import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import com.pokerogue.helper.pokemon.repository.PokemonAbilityMappingRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PokemonAbilityRepositoryTest extends RepositoryTest {

    @Autowired
    private PokemonAbilityRepository pokemonAbilityRepository;

    @Autowired
    private PokemonAbilityMappingRepository pokemonAbilityMappingRepository;

    @Test
    @DisplayName("특성에 해당하는 포켓몬과 포켓몬 타입들을 조회한다.")
    void findByIdWithPokemonAndPokemonTypes() {
        PokemonAbility pokemonAbility = pokemonAbilityRepository.findAll().get(0);
        Long pokemonAbilityId = pokemonAbility.getId();

        PokemonAbility queriedAbility = pokemonAbilityRepository.findByIdWithPokemonAndPokemonTypes(pokemonAbilityId)
                .get();
        Set<PokemonAbilityMapping> pokemonAbilityMappings = queriedAbility.getPokemonAbilityMappings();
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

    @Test
    @DisplayName("포켓몬이 없는 특성을 조회한다.")
    void findByIdNotHavingPokemon() {
        Long abilityId = findAbilityIdNotHavingPokemon();

        Optional<PokemonAbility> queriedAbility = pokemonAbilityRepository.findByIdWithPokemonAndPokemonTypes(
                abilityId);

        assertThat(queriedAbility).isNotEmpty();
    }

    private Long findAbilityIdNotHavingPokemon() {
        List<Long> allAbilityIds = pokemonAbilityRepository.findAll().stream()
                .map(PokemonAbility::getId)
                .toList();
        Set<Long> abilityIdsHavingPokemon = pokemonAbilityMappingRepository.findAll().stream()
                .map(PokemonAbilityMapping::getPokemonAbility)
                .map(PokemonAbility::getId)
                .collect(Collectors.toCollection(HashSet::new));
        return allAbilityIds.stream()
                .filter(id -> !abilityIdsHavingPokemon.contains(id))
                .findAny()
                .get();
    }
}
