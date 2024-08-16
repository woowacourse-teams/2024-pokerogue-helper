package com.pokerogue.helper.biome.dto;

import com.pokerogue.helper.biome.data.Biome;
import java.util.List;

public record BiomeResponse(
        String id,
        String name,
        String image,
        List<String> pokemonTypeLogos,
        List<String> trainerTypeLogos
) {

    public static BiomeResponse from(Biome biome) {
        return new BiomeResponse(
                biome.getId(),
                biome.getName(),
                biome.getImage(),
                biome.getMainTypes(),
                biome.getTrainerTypes()
        );
    }
}
