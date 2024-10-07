package com.pokerogue.helper.pokemon.service;

import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.FakeEvolution;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EvolutionContextTest {

    @DisplayName("각 노드의 깊이 정보를 가져올 수 있다.")
    @Test
    void getDepthOf() {
        Evolution evolution = FakeEvolution.from("A", "B");
        Evolution evolution2 = FakeEvolution.from("B", "C");
        Evolution evolution3 = FakeEvolution.from("C", "D");
        Evolution evolution4 = FakeEvolution.from("D", "E");

        EvolutionContext context = new EvolutionContext(List.of(evolution, evolution2, evolution3, evolution4));

        Assertions.assertThat(context.getDepthOf("A")).isEqualTo(0);
        Assertions.assertThat(context.getDepthOf("B")).isEqualTo(1);
        Assertions.assertThat(context.getDepthOf("C")).isEqualTo(2);
        Assertions.assertThat(context.getDepthOf("D")).isEqualTo(3);
        Assertions.assertThat(context.getDepthOf("E")).isEqualTo(4);
    }

    @DisplayName("포켓몬의 진화 정보를 가져올 수 있다.")
    @Test
    void getEvolutionOf() {
        Evolution actual = FakeEvolution.from("A", "B");

        EvolutionContext context = new EvolutionContext(List.of(actual));
        Evolution expected = context.getEvolutionOf("A");

        Assertions.assertThat(expected).isEqualTo(actual);
    }
}
