package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;

import java.util.List;

public record PokemonResponse(
        Long id,
        Long pokedexNumber,
        String koName,
        String image,
        List<PokemonTypeResponse> pokemonTypeResponses
) {

    public static PokemonResponse of(Pokemon pokemon, List<PokemonTypeResponse> pokemonTypeResponses) {
        return new PokemonResponse(pokemon.getId(), pokemon.getPokedexNumber(), pokemon.getKoName(), pokemon.getImage(),
                pokemonTypeResponses);
    }
}
