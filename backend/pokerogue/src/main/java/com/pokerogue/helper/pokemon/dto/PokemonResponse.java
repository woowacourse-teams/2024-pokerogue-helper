package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.pokemon.domain.Pokemon;
import java.util.List;

public record PokemonResponse(Long id, Long pokedexNumber, String name, String image, List<String> pokemonTypeImages) {

    public static PokemonResponse of(Pokemon pokemon, List<String> pokemonTypeImages) {
        return new PokemonResponse(pokemon.getId(), pokemon.getPokemonNumber(), pokemon.getName(), pokemon.getImage(),
                pokemonTypeImages);
    }
}
