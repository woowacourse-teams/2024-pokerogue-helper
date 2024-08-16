package com.pokerogue.helper.biome.repository;

import com.pokerogue.helper.biome.domain.Biome;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BiomeRepository {

    private final Map<String, Biome> biomes;

    public BiomeRepository() {
        this.biomes = new HashMap<>();
    }

    public void save(Biome biome) {
        biomes.put(biome.getId(), biome);
    }

    public List<Biome> findAllBiomes() {
        return biomes.values().stream().toList();
    }
}
