package com.pokerogue.helper.biome.dto;

import com.pokerogue.helper.biome.data.Biome;
import java.util.List;

public record BiomeResponse(
        String id,
        String name,
        String image,
        List<BiomeTypeResponse> pokemonTypeResponses,
        List<BiomeTypeResponse> trainerTypeResponses
) {

    public static BiomeResponse of(Biome biome, String biomeImage, List<BiomeTypeResponse> pokemonTypeLogos, List<BiomeTypeResponse> trainerTypeLogos) {
        return new BiomeResponse(
                biome.getId(),
                biome.getKoName(),
                biomeImage,
                pokemonTypeLogos,
                trainerTypeLogos
        );
    }
}
