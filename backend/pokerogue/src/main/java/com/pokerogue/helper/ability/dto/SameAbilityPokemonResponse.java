package com.pokerogue.helper.ability.dto;

import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import com.pokerogue.helper.type.dto.PokemonTypeResponse;
import java.util.List;

public record SameAbilityPokemonResponse(
        Long id,
        Long pokedexNumber,
        String name,
        String image,
        List<PokemonTypeResponse> pokemonTypeResponses
) {
    public static SameAbilityPokemonResponse from(Pokemon pokemon) {
        Long id = pokemon.getId();
        Long pokedexNumber = pokemon.getPokedexNumber();
        String name = pokemon.getName();
        String image = pokemon.getImage();
        List<PokemonTypeResponse> pokemonTypeResponses = pokemon.getPokemonTypeMappings().stream()
                .map(PokemonTypeMapping::getPokemonType)
                .map(PokemonTypeResponse::from)
                .toList();

        return new SameAbilityPokemonResponse(id, pokedexNumber, name, image, pokemonTypeResponses);
    }
}
