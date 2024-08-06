package com.pokerogue.helper.ability.service;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.ability.dto.PokemonAbilityWithPokemonsResponse;
import com.pokerogue.helper.ability.dto.SameAbilityPokemonResponse;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PokemonAbilityServiceTest extends ServiceTest {

    @Autowired
    private PokemonAbilityService pokemonAbilityService;

    @Autowired
    private PokemonAbilityRepository pokemonAbilityRepository;

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

        PokemonAbilityWithPokemonsResponse pokemonAbilityWithPokemons = pokemonAbilityService.findAbilityDetails(abilityId);

        List<Long> pokemonIds = pokemonAbilityWithPokemons.pokemons().stream()
                .map(SameAbilityPokemonResponse::id)
                .toList();
        assertThat(pokemonIds).isSortedAccordingTo(Long::compare);
    }
}
