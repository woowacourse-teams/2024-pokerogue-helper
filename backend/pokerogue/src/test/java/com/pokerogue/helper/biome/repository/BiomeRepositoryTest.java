package com.pokerogue.helper.biome.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.repository.MongoRepositoryTest;
import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.biome.data.NativePokemon;
import com.pokerogue.helper.biome.data.Tier;
import com.pokerogue.helper.biome.data.Trainer;
import com.pokerogue.helper.type.data.Type;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BiomeRepositoryTest extends MongoRepositoryTest {

    @Autowired
    private BiomeRepository biomeRepository;

    @Test
    @DisplayName("모든 바이옴 정보가 정상적으로 매핑되는지 확인한다.")
    void findAll_AndConvertToBiome() {
        List<Biome> biomes = biomeRepository.findAll();

        assertAll(
                () -> assertThat(biomes).hasSize(35),
                () -> assertThat(biomes.stream()
                        .flatMap(biome -> biome.getTypes().stream()))
                        .allMatch(type -> type.getDeclaringClass()
                                .equals(Type.class)),
                () -> assertThat(biomes.stream()
                        .flatMap(biome -> biome.getNativePokemons().stream()
                                .map(NativePokemon::getTier))
                        .allMatch(tier -> tier.getDeclaringClass()
                                .equals(Tier.class)))
                        .isTrue(),
                () -> assertThat(biomes.stream()
                        .flatMap(biome -> biome.getTrainers().stream()
                                .map(Trainer::getTypes))
                        .flatMap(Collection::stream)
                        .allMatch(type -> type.getDeclaringClass()
                                .equals(Type.class)))
                        .isTrue()
        );
    }
}
