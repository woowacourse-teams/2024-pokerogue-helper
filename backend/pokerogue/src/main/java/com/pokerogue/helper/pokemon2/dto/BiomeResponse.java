package com.pokerogue.helper.pokemon2.dto;

import com.pokerogue.helper.pokemon2.data.Biome;

public record BiomeResponse(String id, String name, String image) {

    public static BiomeResponse from(String id) {
        Biome biome = Biome.findById(id);
        return new BiomeResponse(id, biome.getName(), "URL" + id);
    }
}
