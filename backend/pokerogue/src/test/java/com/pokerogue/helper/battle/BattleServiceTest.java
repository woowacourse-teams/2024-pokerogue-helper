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

    @Autowired
    private PokemonMovesByMachineRepository pokemonMovesByMachineRepository;

    @Test
    @DisplayName("포켓몬의 기술(자력 기술, 머신 기술, 알 기술) 리스트를 조회한다.")
    void findMovesByPokemon() {
        List<Integer> pokedexNumbers = pokemonMovesByMachineRepository.findAll()
                .stream()
                .map(PokemonMovesByMachine::pokedexNumber)
                .toList();

        pokedexNumbers.forEach(pokedexNumber -> {
            List<MoveResponse> moveResponses = battleService.findMovesByPokemon(pokedexNumber);
            assertThat(moveResponses).isNotEmpty();
        });
    }
}
