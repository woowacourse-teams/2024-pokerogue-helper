package com.pokerogue.helper.type.dto;

import com.pokerogue.helper.type.domain.PokemonTypeMatching;

public record PokemonMatchingResponse(String from, String to, Double result) {
    public static PokemonMatchingResponse from(PokemonTypeMatching pokemonTypeMatching) {
        return new PokemonMatchingResponse(
                pokemonTypeMatching.getTypeFrom(),
                pokemonTypeMatching.getTypeTo(),
                pokemonTypeMatching.getResult()
        );
    }
}
