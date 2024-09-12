package com.pokerogue.helper.battle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

class DataInitializerTest {

    @Test
    @DisplayName("날씨, 기술 데이터를 세팅한다.")
    void setWeathersData() {
        BattleMoveRepository battleMoveRepository = new BattleMoveRepository();
        TypeMatchingRepository typeMatchingRepository = new TypeMatchingRepository();
        DataInitializer dataInitializer = new DataInitializer(
                battleMoveRepository,
                typeMatchingRepository
        );
        dataInitializer.run(new DefaultApplicationArguments());

        assertAll(() -> {
            assertThat(battleMoveRepository.findAll()).hasSize(920);
            assertThat(typeMatchingRepository.findAll()).hasSize(361);
        });
    }
}
