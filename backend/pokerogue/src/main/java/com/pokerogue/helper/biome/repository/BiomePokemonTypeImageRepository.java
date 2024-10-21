package com.pokerogue.helper.biome.repository;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BiomePokemonTypeImageRepository {

    private final Map<String, String> biomePokemonTypeImages;

    public BiomePokemonTypeImageRepository() {
        this.biomePokemonTypeImages = new HashMap<>();
    }

    public void save(String typeName, String typeImageUrl) {
        biomePokemonTypeImages.put(typeName, typeImageUrl);
    }

    public String findPokemonTypeImageUrl(String typeName) {
        return biomePokemonTypeImages.get(typeName);
    }
}
