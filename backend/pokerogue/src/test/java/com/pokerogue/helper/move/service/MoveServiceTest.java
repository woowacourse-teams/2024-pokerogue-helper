package com.pokerogue.helper.move.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
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

    @Test
    @DisplayName("단일 기술 정보를 불러온다")
    void findMove() {
        MoveResponse moveResponse = moveService.findMove("earth_power");

        assertAll(
                () -> assertThat(moveResponse.id()).isEqualTo("earth_power"),
                () -> assertThat(moveResponse.name()).isEqualTo("대지의힘"),
                () -> assertThat(moveResponse.typeEngName()).isEqualTo("ground"),
                () -> assertThat(moveResponse.typeLogo()).contains("type/ground"),
                () -> assertThat(moveResponse.categoryEngName()).isEqualTo("special"),
                () -> assertThat(moveResponse.categoryLogo()).contains("move-category/special.png"),
                () -> assertThat(moveResponse.power()).isEqualTo(90),
                () -> assertThat(moveResponse.accuracy()).isEqualTo(100),
                () -> assertThat(moveResponse.effect()).isEqualTo("상대의 발밑에 대지의 힘을 방출한다. 상대의 특수방어를 떨어뜨릴 때가 있다.")
        );
    }

    @Test
    @DisplayName("id에 해당하는 기술이 없는 경우 예외를 발생시킨다")
    void notExistMove() {
        assertThatThrownBy(() -> moveService.findMove("test"))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.MOVE_NOT_FOUND.getMessage());
    }
}
