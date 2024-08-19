package com.pokerogue.helper.biome.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.environment.service.ServiceTest;
import com.pokerogue.helper.biome.repository.BiomeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;

class BiomeDatabaseInitializerTest extends ServiceTest {

    @Autowired
    private BiomeDatabaseInitializer biomeDatabaseInitializer;

    @Autowired
    private BiomeRepository biomeRepository;

    @Test
    @DisplayName("바이옴 데이터를 세팅한다.")
    void setBiomesData() {
        biomeDatabaseInitializer.run(new DefaultApplicationArguments());

        assertThat(biomeRepository.findAll()).hasSize(35);
    }
}
