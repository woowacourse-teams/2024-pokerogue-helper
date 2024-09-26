package com.pokerogue.helper.biome.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.biome.dto.BiomeResponse;
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

    /*@Test
    @DisplayName("단일 바이옴 정보를 불러온다")
    void findBiome() {
        BiomeDetailResponse biomeDetailResponse = biomeService.findBiome("fairy_cave");

        assertAll(
                () -> assertThat(biomeDetailResponse.id()).isEqualTo("fairy_cave"),
                () -> assertThat(biomeDetailResponse.name()).isEqualTo("페어리 동굴"),
                () -> assertThat(biomeDetailResponse.image()).contains("/biome/fairy_cave.png"),
                () -> assertThat(biomeDetailResponse.wildPokemons()).hasSize(5),
                () -> assertThat(biomeDetailResponse.bossPokemons()).hasSize(4),
                () -> assertThat(biomeDetailResponse.trainerPokemons()).hasSize(3),
                () -> assertThat(biomeDetailResponse.nextBiomes()).hasSize(2)
        );
    }*/
}
