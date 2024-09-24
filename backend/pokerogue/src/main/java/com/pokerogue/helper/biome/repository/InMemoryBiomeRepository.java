package com.pokerogue.helper.biome.repository;

import com.pokerogue.helper.biome.data.Biome;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InMemoryBiomeRepository {

    private final Map<String, Biome> biomes;

    public InMemoryBiomeRepository() {
        this.biomes = new HashMap<>();
    }

    public void save(Biome biome) {
        biomes.put(biome.getId(), biome);
    }

    public List<Biome> findAll() {
        return biomes.values().stream()
                .toList();
    }

    public Optional<Biome> findById(String id) {
        return Optional.ofNullable(biomes.get(id));
    }
}
