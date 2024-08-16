package com.pokerogue.helper.biome.dto;

import com.pokerogue.helper.biome.domain.Biome;
import java.util.List;

public record BiomeResponse(String id, String name, String image, List<String> pokemonTypeLogos, List<String> trainerTypeLogos) {

    public static BiomeResponse from(Biome biome) {
        return new BiomeResponse(biome.getId(), biome.getName(), "바이옴 이미지", biome.getMainTypes(), biome.getTrainerTypes());
    }
}
