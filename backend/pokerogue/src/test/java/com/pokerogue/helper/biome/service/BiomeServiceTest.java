package com.pokerogue.helper.biome.service;

import static com.pokerogue.helper.global.constant.SortingCriteria.ASCENDING;
import static com.pokerogue.helper.global.constant.SortingCriteria.DESCENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.biome.dto.BiomeAllPokemonResponse;
import com.pokerogue.helper.biome.dto.BiomeDetailResponse;
import com.pokerogue.helper.biome.dto.BiomeResponse;
import com.pokerogue.helper.global.constant.SortingCriteria;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BiomeServiceTest extends ServiceTest {

    @Autowired
    private BiomeService biomeService;

    @Test
    @DisplayName("전체 바이옴 리스트를 불러온다")
    void findBoimes() {
        List<BiomeResponse> biomes = biomeService.findBiomes();

        assertThat(biomes.size()).isEqualTo(35);
    }

    @Test
    @DisplayName("단일 바이옴 정보를 불러온다")
    void findBiome() {
        BiomeDetailResponse biomeDetailResponse = biomeService.findBiome("fairy_cave", ASCENDING, DESCENDING);

        assertAll(
                () -> assertThat(biomeDetailResponse.id()).isEqualTo("fairy_cave"),
                () -> assertThat(biomeDetailResponse.name()).isEqualTo("Fairy Cave"),
                () -> assertThat(biomeDetailResponse.wildPokemons()).hasSize(5),
                () -> assertThat(biomeDetailResponse.bossPokemons()).hasSize(4),
                () -> assertThat(biomeDetailResponse.trainerPokemons()).hasSize(3),
                () -> assertThat(biomeDetailResponse.nextBiomes()).hasSize(2)
        );
    }

    @Test
    @DisplayName("해당 id의 바이옴이 없는 경우 예외를 발생시킨다")
    void notExistBiome() {
        assertThatThrownBy(() -> biomeService.findBiome("test", ASCENDING, DESCENDING))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.BIOME_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("바이옴 포켓몬의 티어를 희귀도 순으로 정렬한다.")
    void sortBiomeNativePokemons() {
        SortingCriteria bossPokemonOrder = DESCENDING;
        SortingCriteria wildPokemonOrder = ASCENDING;

        BiomeDetailResponse biomeDetailResponse = biomeService.findBiome("fairy_cave", bossPokemonOrder,
                wildPokemonOrder);

        assertAll(() -> {
            assertThat(biomeDetailResponse.wildPokemons()).extracting(BiomeAllPokemonResponse::tier)
                    .containsExactly("보통", "드묾", "레어", "슈퍼 레어", "울트라 레어");
            assertThat(biomeDetailResponse.bossPokemons()).extracting(BiomeAllPokemonResponse::tier)
                    .containsExactly("슈퍼 울트라 레어 보스", "슈퍼 레어 보스", "레어 보스", "보스");
        });
    }
}
