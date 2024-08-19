package com.pokerogue.helper.biome.dto;

import java.util.List;

public record BiomePokemonResponse(
        String id,
        String name,
        String image,
        List<BiomePokemonTypeResponse> pokemonTypeResponses
) {
}
