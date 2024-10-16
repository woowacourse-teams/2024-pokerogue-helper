package com.pokerogue.helper.biome.service;

import static com.pokerogue.helper.global.constant.SortingCriteria.ASCENDING;
import static com.pokerogue.helper.global.constant.SortingCriteria.DESCENDING;
import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.biome.data.NativePokemon;
import com.pokerogue.helper.biome.data.Tier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NativePokemonComparatorTest {

    private final List<NativePokemon> nativePokemons = new ArrayList<>(List.of(
            new NativePokemon(Tier.RARE, List.of("bulbasaur", "venusaur")),
            new NativePokemon(Tier.COMMON, List.of("pikachu", "raichu")),
            new NativePokemon(Tier.BOSS_RARE, List.of("charmander")),
            new NativePokemon(Tier.BOSS_SUPER_RARE, List.of("roserade"))
    ));

    @Test
    @DisplayName("바이옴 포켓몬을 티어의 희귀도가 낮은 순으로 정렬한다.")
    void sortNativePokemonsAscending() {
        Collections.sort(nativePokemons, NativePokemonComparator.of(ASCENDING));

        assertThat(nativePokemons).extracting(NativePokemon::getTier)
                .containsExactly(Tier.COMMON, Tier.RARE, Tier.BOSS_RARE, Tier.BOSS_SUPER_RARE);
    }

    @Test
    @DisplayName("바이옴 포켓몬을 티어의 희귀도가 높은 순으로 정렬한다.")
    void sortNativePokemonsDescending() {
        Collections.sort(nativePokemons, NativePokemonComparator.of(DESCENDING));

        assertThat(nativePokemons).extracting(NativePokemon::getTier)
                .containsExactly(Tier.BOSS_SUPER_RARE, Tier.BOSS_RARE, Tier.RARE, Tier.COMMON);
    }
}
