package com.pokerogue.helper.pokemon.dto;

import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;

public record PokedexResponse(Long pokedexNumber, String name, String pokemonImage,
                              List<PokemonTypeResponse> pokemonTypeResponses, List<String> pokemonAbilityNames,
                              Integer weight, Integer height) {

    public static PokedexResponse of(Pokemon pokemon, List<PokemonTypeResponse> pokemonTypeResponses,
                                     List<String> pokemonAbilityNames) {
        return new PokedexResponse(pokemon.getPokemonNumber(), pokemon.getName(), pokemon.getImage(),
                pokemonTypeResponses, pokemonAbilityNames, pokemon.getWeight(), pokemon.getHeight());
    }
}
