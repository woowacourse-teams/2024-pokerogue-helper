package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.biome.data.Biome;
import com.pokerogue.helper.global.config.ImageUrl;

public record PokemonBiomeResponse(String id, String name, String image) {
    public static PokemonBiomeResponse from(Biome biome) {
        return new PokemonBiomeResponse(biome.getIndex(), biome.getName(), ImageUrl.getBiomeImage(biome.getIndex()));
    }
}
