package com.pokerogue.helper.pokemon.service;

import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.data.Evolution;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EvolutionContextTest {

    @DisplayName("각 포켓몬 진화 깊이 정보를 가져올 수 있다.")
    @Test
    void getDepthOf() {
        Evolution evolution = createEvolution("A", "B");
        Evolution evolution2 = createEvolution("B", "C");
        Evolution evolution3 = createEvolution("C", "D");
        Evolution evolution4 = createEvolution("D", "E");

        EvolutionContext context = new EvolutionContext(List.of(evolution, evolution2, evolution3, evolution4));

        assertAll(() -> {
            Assertions.assertThat(context.getDepthOf("A")).isEqualTo(0);
            Assertions.assertThat(context.getDepthOf("B")).isEqualTo(1);
            Assertions.assertThat(context.getDepthOf("C")).isEqualTo(2);
            Assertions.assertThat(context.getDepthOf("D")).isEqualTo(3);
            Assertions.assertThat(context.getDepthOf("E")).isEqualTo(4);
        });
    }

    @DisplayName("포켓몬의 진화 정보를 가져올 수 있다.")
    @Test
    void getEvolutionOf() {
        Evolution actual = createEvolution("A", "B");

        EvolutionContext context = new EvolutionContext(List.of(actual));
        Evolution expected = context.getEvolutionOf("A");

        Assertions.assertThat(expected).isEqualTo(actual);
    }

    @DisplayName("잘못된 포켓몬 아이디라면 예외가 발생한다.")
    @Test
    void getEvolutionOfException() {
        Evolution actual = createEvolution("A", "B");
        EvolutionContext context = new EvolutionContext(List.of(actual));

        Assertions.assertThatThrownBy(() -> context.getEvolutionOf("C"))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.POKEMON_NOT_FOUND.getMessage());
    }

    private static Evolution createEvolution(String from, String to) {
        return new Evolution(from, 1, to, "", "");
    }
}
