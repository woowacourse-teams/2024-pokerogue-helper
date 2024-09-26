package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.biome.data.Biome;
import java.util.Optional;

public record PokemonBiomeResponse(String id, String name, String image) {
    private static final PokemonBiomeResponse EMPTY = new PokemonBiomeResponse("", "", "");

    public static PokemonBiomeResponse from(Optional<Biome> optionalBiome) {
        if (optionalBiome.isEmpty()) {
            return EMPTY;
        }
        Biome biome = optionalBiome.get();

        return new PokemonBiomeResponse(biome.getId(), biome.getKoName(), biome.getImage());
    }
}
