package com.pokerogue.helper.battle.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BattleServiceTest extends ServiceTest {

    @Autowired
    private BattleService battleService;

    @Test
    @DisplayName("배틀 결과 계산에서 id로 날씨를 찾지 못하면 예외가 발생한다.")
    void findWeatherByIdWhenCalculateBattleResult() {
        String wrongWeatherId = "cloud";
        String myPokemonId = "charmander";
        String rivalPokemonId = "bulbasaur";
        String myMoveId = "ember";

        assertThatThrownBy(
                () -> battleService.calculateBattleResult(wrongWeatherId, myPokemonId, rivalPokemonId, myMoveId))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.WEATHER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("배틀 결과 계산에서 id로 포켓몬을 찾지 못하면 예외가 발생한다.")
    void findPokemonByIdWhenCalculateBattleResult() {
        String weatherId = "sunny";
        String wrongMyPokemonId = "mia";
        String rivalPokemonId = "bulbasaur";
        String myMoveId = "ember";

        assertThatThrownBy(
                () -> battleService.calculateBattleResult(weatherId, wrongMyPokemonId, rivalPokemonId, myMoveId))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.POKEMON_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("배틀 결과 계산에서 id로 기술을 찾지 못하면 예외가 발생한다.")
    void findMoveByIdWhenCalculateBattleResult() {
        String weatherId = "sunny";
        String myPokemonId = "charmander";
        String rivalPokemonId = "bulbasaur";
        String wrongMyMoveId = "punch";

        assertThatThrownBy(
                () -> battleService.calculateBattleResult(weatherId, myPokemonId, rivalPokemonId, wrongMyMoveId))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.MOVE_NOT_FOUND.getMessage());
    }
}
