package com.pokerogue.helper.pokemon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TreeDepthCalculatorTest {

    @DisplayName("인접한 간선의 정보가 주어지면 각 노드의 깊이를 구할 수 있다")
    @Test
    void calculateDepths() {
        Map<String, List<String>> adjacentNodes = new HashMap<>(Map.of(
                "A", List.of("B", "C", "D", "E"),
                "B", List.of("C", "D", "E"),
                "C", List.of("D", "E"),
                "D", List.of("E")
        ));
        Map<String, Integer> expected = new HashMap<>(Map.of(
                "A", 0,
                "B", 1,
                "C", 2,
                "D", 3,
                "E", 4
        ));

        Map<String, Integer> actual = new TreeDepthCalculator(adjacentNodes).calculateDepths();

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
