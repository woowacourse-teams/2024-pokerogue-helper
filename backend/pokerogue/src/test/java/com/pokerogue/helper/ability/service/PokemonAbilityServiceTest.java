package com.pokerogue.helper.ability.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PokemonAbilityServiceTest extends ServiceTest {

    @Autowired
    private PokemonAbilityService pokemonAbilityService;

    @Test
    @DisplayName("존재하지 않는 포켓몬의 특성을 조회하면 예외가 발생한다.")
    void findAbilityDetailsWhenAbilityIdNotExist() {
        assertThatThrownBy(() -> pokemonAbilityService.findAbilityDetails(Long.MAX_VALUE))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.POKEMON_ABILITY_NOT_FOUND.getMessage());
    }
}
