package com.pokerogue.helper.battle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.pokemon.data.InMemoryPokemon;
import com.pokerogue.helper.pokemon.repository.InMemoryPokemonRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BattleServiceTest extends ServiceTest {

    @Autowired
    private BattleService battleService;

    @Autowired
    private InMemoryPokemonRepository inMemoryPokemonRepository;

    @Test
    @DisplayName("포켓몬의 기술(자력 기술, 머신 기술, 알 기술) 리스트를 조회한다.")
    void findMovesByPokemon() {
        List<String> pokemonIds = inMemoryPokemonRepository.findAll().values()
                .stream()
                .map(InMemoryPokemon::id)
                .toList();

        pokemonIds.forEach(pokemonId -> {
            List<MoveResponse> moveResponses = battleService.findMovesByPokemon(pokemonId);
            assertThat(moveResponses).isNotEmpty();
        });
    }

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
