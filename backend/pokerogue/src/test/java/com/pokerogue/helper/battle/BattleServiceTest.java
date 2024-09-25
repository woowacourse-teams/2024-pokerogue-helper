package com.pokerogue.helper.battle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.battle.dto.BattleResultResponse;
import com.pokerogue.helper.battle.service.BattleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BattleServiceTest extends ServiceTest {

    @Autowired
    private BattleService battleService;

    @Test
    @DisplayName("배틀 예상 결과를 계산한다.")
    void calculateBattleResult() {
        String weatherId = "sunny";
        String myPokemonId = "charmander";
        String rivalPokemonId = "bulbasaur";
        String myMoveId = "ember";

        BattleResultResponse battleResultResponse = battleService.calculateBattleResult(weatherId, myPokemonId,
                rivalPokemonId, myMoveId);

        assertAll(() -> {
            assertThat(battleResultResponse.multiplier()).isEqualTo(4.5);
            assertThat(battleResultResponse.accuracy()).isEqualTo(100);
        });
    }
}
