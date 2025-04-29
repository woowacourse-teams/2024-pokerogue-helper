package com.pokerogue.helper.ability.dto;

import com.pokerogue.helper.pokemon.data.Pokemon;
import java.util.List;

public record AbilityPokemonResponse(
        String id,
        Long pokedexNumber,
        String koName,
        String image,
        List<AbilityTypeResponse> pokemonTypeResponses
) {
    public static AbilityPokemonResponse of(
            Pokemon pokemon,
            String image,
            List<AbilityTypeResponse> pokemonTypeResponses
    ) {
        return new AbilityPokemonResponse(
                pokemon.getId(),
                (long) pokemon.getPokedexNumber(),
                pokemon.getName(),
                image,
                pokemonTypeResponses
        );
    }
}
