package com.pokerogue.helper.pokemon.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.pokemon.data.EvolutionItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EvolutionItemConverterTest {

    @Test
    @DisplayName("들어온 겂을 EvolutionItem Enum으로 변화한다.")
    void convert() {
        EvolutionItemConverter evolutionItemConverter = new EvolutionItemConverter();

        assertThat(evolutionItemConverter.convert("dusk_stone")).isEqualTo(EvolutionItem.DUSK_STONE);
    }
}
