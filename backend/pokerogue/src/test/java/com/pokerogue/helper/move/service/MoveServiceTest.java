package com.pokerogue.helper.move.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.move.dto.MoveDetailResponse;
import com.pokerogue.helper.move.dto.MoveResponse;
import java.util.List;
import org.junit.jupiter.api.Disabled;
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
    @DisplayName("배틀 서비스에서 사용할 단일 기술 정보를 불러온다")
    void findMoveByBattle() {
        MoveResponse moveResponse = moveService.findMoveInBattle("earth_power");

        assertAll(
                () -> assertThat(moveResponse.id()).isEqualTo("earth_power"),
                () -> assertThat(moveResponse.name()).isEqualTo("Earth Power"),
                () -> assertThat(moveResponse.typeEngName()).isEqualTo("ground"),
                () -> assertThat(moveResponse.typeLogo()).contains("type/ground"),
                () -> assertThat(moveResponse.categoryEngName()).isEqualTo("special"),
                () -> assertThat(moveResponse.categoryLogo()).contains("move-category/special.png"),
                () -> assertThat(moveResponse.power()).isEqualTo(90),
                () -> assertThat(moveResponse.accuracy()).isEqualTo(100),
                () -> assertThat(moveResponse.effect()).isEqualTo("The user makes the ground under the target erupt with power. This may also lower the target's Sp. Def stat.")
        );
    }

    @Test
    @DisplayName("id에 해당하는 기술이 없는 경우 예외를 발생시킨다")
    void notExistMove() {
        assertThatThrownBy(() -> moveService.findMoveInBattle("test"))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.MOVE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("전체 기술 목록을 반환한다")
    void findMoves() {
        List<MoveResponse> movesByPokemon = moveService.findMoves();

        assertThat(movesByPokemon.size()).isEqualTo(919);
    }

    @Disabled
    @Test
    @DisplayName("단일 기술 정보를 불러온다")
    void findMove() {
        MoveDetailResponse moveDetailResponse = moveService.findMove("earth_power");

        assertAll(
                () -> assertThat(moveDetailResponse.id()).isEqualTo("earth_power"),
                () -> assertThat(moveDetailResponse.name()).isEqualTo("대지의힘"),
                () -> assertThat(moveDetailResponse.typeEngName()).isEqualTo("ground"),
                () -> assertThat(moveDetailResponse.typeLogo()).contains("type/ground"),
                () -> assertThat(moveDetailResponse.categoryEngName()).isEqualTo("special"),
                () -> assertThat(moveDetailResponse.categoryLogo()).contains("move-category/special.png"),
                () -> assertThat(moveDetailResponse.moveTarget()).isEqualTo("near_other"),
                () -> assertThat(moveDetailResponse.power()).isEqualTo(90),
                () -> assertThat(moveDetailResponse.accuracy()).isEqualTo(100),
                () -> assertThat(moveDetailResponse.powerPoint()).isEqualTo(10),
                () -> assertThat(moveDetailResponse.effect()).isEqualTo("상대의 발밑에 대지의 힘을 방출한다. 상대의 특수방어를 떨어뜨릴 때가 있다."),
                () -> assertThat(moveDetailResponse.effectChance()).isEqualTo(10),
                () -> assertThat(moveDetailResponse.priority()).isEqualTo(0),
                () -> assertThat(moveDetailResponse.generation()).isEqualTo(4),
                () -> assertThat(moveDetailResponse.released()).isNull(),
                () -> assertThat(moveDetailResponse.flags()).isEmpty(),
                () -> assertThat(moveDetailResponse.pokemonIdsWithLevelMove()).hasSize(71),
                () -> assertThat(moveDetailResponse.pokemonIdsWithEggMove()).hasSize(117)
        );
    }
}
