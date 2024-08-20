package com.pokerogue.helper.biome.dto;

import com.pokerogue.helper.biome.data.Biome;

public record NextBiomeResponse(String id, String name, String image, String percent) {

    public static NextBiomeResponse of(Biome biome, String percent) {
        return new NextBiomeResponse(biome.getId(), biome.getName(), biome.getImage(), percent);
    }
}
