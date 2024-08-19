package com.pokerogue.helper.biome.repository;

import com.pokerogue.helper.biome.data.BiomePokemon;
import com.pokerogue.helper.biome.data.BiomePokemonInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BiomePokemonInfoRepository {

    private final Map<String, BiomePokemonInfo> biomePokemonInfos;

    public BiomePokemonInfoRepository() {
        this.biomePokemonInfos = new HashMap<>();
    }

    public void save(BiomePokemonInfo biomePokemonInfo) {
        biomePokemonInfos.put(biomePokemonInfo.getId(), biomePokemonInfo);
    }

    public Optional<BiomePokemonInfo> findById(String id) {
        return Optional.ofNullable(biomePokemonInfos.get(id));
    }
}
