package com.pokerogue.helper.ability2.dto;

import java.util.List;

public record AbilityPokemonResponse(
        String id,
        Long pokedexNumber,
        String koName,
        String image,
        List<AbilityTypeResponse> pokemonTypeResponses
) {
}
