package com.pokerogue.helper.ability.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.ability.dto.PokemonAbilityWithPokemonsResponse;
import com.pokerogue.helper.ability.dto.SameAbilityPokemonResponse;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import com.pokerogue.helper.type.repository.PokemonTypeRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PokemonAbilityServiceTest extends ServiceTest {

    @Autowired
    private PokemonAbilityService pokemonAbilityService;

    @Autowired
    private PokemonAbilityRepository pokemonAbilityRepository;

    @Autowired
    private PokemonTypeRepository pokemonTypeRepository;

    @Test
    @DisplayName("존재하지 않는 포켓몬의 특성을 조회하면 예외가 발생한다.")
    void findAbilityDetailsWhenAbilityIdNotExist() {
        assertThatThrownBy(() -> pokemonAbilityService.findAbilityDetails(Long.MAX_VALUE))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.POKEMON_ABILITY_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("같은 특성을 가진 포켓몬 목록을 조회할 때 id 순으로 정렬한다.")
    void findPokemonsOrderByIdInAbilityDetails() {
        Long abilityId = pokemonAbilityRepository.findAll().get(0).getId();

        PokemonAbilityWithPokemonsResponse pokemonAbilityWithPokemons = pokemonAbilityService.findAbilityDetails(
                abilityId);

        List<Long> pokemonIds = pokemonAbilityWithPokemons.pokemons().stream()
                .map(SameAbilityPokemonResponse::id)
                .toList();
        assertThat(pokemonIds).isSortedAccordingTo(Long::compare);
    }

    @Test
    @DisplayName("같은 특성을 가진 포켓몬 목록을 조회할 때 포켓몬의 타입을 id 순으로 정렬한다.")
    void findPokemonsWithTypesOrderByIdInAbilityDetails() {
        Long abilityId = pokemonAbilityRepository.findAll().get(0).getId();

        PokemonAbilityWithPokemonsResponse pokemonAbilityWithPokemons = pokemonAbilityService.findAbilityDetails(
                abilityId);

        List<List<String>> pokemonTypeNamesInResponse = pokemonTypeNamesGroupByPokemon(pokemonAbilityWithPokemons);
        Map<String, Long> allPokemonTypeNamesWithId = findAllPokemonTypeNamesWithId();
        assertAll(() -> pokemonTypeNamesInResponse.forEach(typeNames ->
                assertThat(typeNames).isSortedAccordingTo(Comparator.comparingLong(allPokemonTypeNamesWithId::get))));
    }

    private List<List<String>> pokemonTypeNamesGroupByPokemon(
            PokemonAbilityWithPokemonsResponse pokemonAbilityWithPokemons) {
        return pokemonAbilityWithPokemons.pokemons().stream()
                .map(SameAbilityPokemonResponse::pokemonTypeResponses)
                .map(pokemonTypeResponses -> pokemonTypeResponses.stream()
                        .map(PokemonTypeResponse::typeName)
                        .toList())
                .toList();
    }

    private Map<String, Long> findAllPokemonTypeNamesWithId() {
        return pokemonTypeRepository.findAll().stream()
                .collect(Collectors.toMap(PokemonType::getName, PokemonType::getId));
    }
}
