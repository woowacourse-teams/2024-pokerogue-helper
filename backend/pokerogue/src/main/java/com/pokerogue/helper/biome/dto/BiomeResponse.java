package com.pokerogue.helper.biome.dto;

import java.util.List;

public record BiomeResponse(String id, String name, String image, List<String> pokemonTypeLogos, List<String> trainerTypeLogos) {
}
