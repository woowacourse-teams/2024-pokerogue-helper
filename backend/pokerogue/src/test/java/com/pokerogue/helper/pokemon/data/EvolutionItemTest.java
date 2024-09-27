package com.pokerogue.helper.pokemon.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EvolutionItemTest {

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "poa", "polla"})
    @DisplayName("존재하지 않는 아이템인 경우 빈 것을 의미하는 값을 반환한다.")
    void convertFrom_WhenNotExit(String inputEvolutionItem) {
        assertThat(EvolutionItem.convertFrom(inputEvolutionItem))
                .isEqualTo(EvolutionItem.EMPTY);
    }

    @Test
    @DisplayName("일치하는 아이템을 찾아온다.")
    void convertFrom() {
        assertThat(EvolutionItem.convertFrom("dawn_stone"))
                .isEqualTo(EvolutionItem.DAWN_STONE);
    }
}
