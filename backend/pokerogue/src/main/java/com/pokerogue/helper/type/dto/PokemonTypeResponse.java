package com.pokerogue.helper.type.dto;

import com.pokerogue.helper.pokemon2.data.Pokemon;
import com.pokerogue.helper.type.domain.PokemonType;

public record PokemonTypeResponse(String pokemonTypeName, String pokemonTypeLogo) {

    public static PokemonTypeResponse from(PokemonType pokemonType) {
        return new PokemonTypeResponse(pokemonType.getName(), pokemonType.getImage());
    }

    public static PokemonTypeResponse from(String pokemonTypeName) {
        return new PokemonTypeResponse(pokemonTypeName, "image");
    }
}
