package com.pokerogue.helper.ability.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.ability.dto.AbilityDetailResponse;
import com.pokerogue.helper.ability.dto.AbilityResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AbilityServiceTest extends ServiceTest {

    @Autowired
    private AbilityService abilityService;

    @Test
    void findAbilities() {
        List<AbilityResponse> abilityResponses = abilityService.findAbilities();

        assertThat(abilityResponses).hasSize(310);
    }

    @Test
    void findAbilityDetails() {
        AbilityDetailResponse abilityDetails = abilityService.findAbilityDetails("stench");

        assertAll(
                () -> assertThat(abilityDetails.koName()).isEqualTo("악취"),
                () -> assertThat(abilityDetails.description()).isEqualTo("악취를 풍겨서 공격했을 때 상대가 풀죽을 때가 있다."),
                () -> assertThat(abilityDetails.pokemons()).hasSize(9)
        );
    }
}
