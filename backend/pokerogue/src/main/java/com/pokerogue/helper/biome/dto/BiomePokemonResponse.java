package com.pokerogue.helper.biome.dto;

import com.pokerogue.helper.biome.data.Tier;
import java.util.List;

public record BiomePokemonResponse(String tier, List<String> pokemons) {

    public static BiomePokemonResponse of(Tier tier, List<String> pokemons) {
        return new BiomePokemonResponse(tier.getName(), pokemons);
    }
}
