package com.pokerogue.helper.biome.dto;

import com.pokerogue.helper.biome.data.Biome;
import java.util.List;

public record NextBiomeResponse(
        String id,
        String name,
        String image,
        String percent,
        List<BiomeTypeResponse> pokemonTypeResponses,
        List<BiomeTypeResponse> trainerTypeResponses
) {

    public static NextBiomeResponse of(
            Biome biome,
            String biomeImage,
            String percent,
            List<BiomeTypeResponse> pokemonTypeResponses,
            List<BiomeTypeResponse> trainerTypeResponses
    ) {
        return new NextBiomeResponse(
                biome.getId(),
                biome.getName(),
                biomeImage,
                percent,
                pokemonTypeResponses,
                trainerTypeResponses
        );
    }
}
