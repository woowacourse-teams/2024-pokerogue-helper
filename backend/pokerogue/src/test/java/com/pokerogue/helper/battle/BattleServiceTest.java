package com.pokerogue.helper.battle;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.environment.service.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BattleServiceTest extends ServiceTest {

    @Autowired
    private BattleService battleService;

    @Test
    @DisplayName("포켓몬의 기술(자력 기술, 머신 기술) 목록을 조회한다.")
    void findMovesByPokemon() {
        Integer pokedexNumber = 1;

        List<MoveResponse> moveResponses = battleService.findMovesByPokemon(pokedexNumber);

        assertThat(moveResponses).isNotEmpty();
    }
}
