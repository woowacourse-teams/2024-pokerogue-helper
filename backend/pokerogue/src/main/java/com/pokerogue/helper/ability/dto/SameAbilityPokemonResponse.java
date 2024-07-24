package com.pokerogue.helper.ability.dto;

import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;

public record SameAbilityPokemonResponse(
        Long id,
        Long pokedexNumber,
        String koName,
        String image,
        List<PokemonTypeResponse> pokemonTypeResponses
) {
    public static SameAbilityPokemonResponse from(Pokemon pokemon, List<PokemonTypeResponse> pokemonTypeResponses) {
        Long id = pokemon.getId();
        Long pokedexNumber = pokemon.getPokedexNumber();
        String koName = pokemon.getKoName();
        String image = pokemon.getImage();

        return new SameAbilityPokemonResponse(id, pokedexNumber, koName, image, pokemonTypeResponses);
    }
}
