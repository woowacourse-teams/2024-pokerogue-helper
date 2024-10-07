package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.biome.data.Biome;

public record PokemonBiomeResponse(String id, String name, String image) {
    public static PokemonBiomeResponse from(Biome biome) {
        return new PokemonBiomeResponse(biome.getId(), biome.getKoName(), biome.getImage());
    }
}
