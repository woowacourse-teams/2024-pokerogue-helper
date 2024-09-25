package com.pokerogue.helper.move.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.move.dto.MoveResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MoveServiceTest extends ServiceTest {

    @Autowired
    private MoveService moveService;

    @Test
    @DisplayName("해당 포켓몬의 전체 기술 목록을 반환한다")
    void findMovesByPokemon() {
        List<MoveResponse> movesByPokemon = moveService.findMovesByPokemon(1);

        assertThat(movesByPokemon.size()).isEqualTo(72);
    }
}
