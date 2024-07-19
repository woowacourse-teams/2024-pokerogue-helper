package com.pokerogue.helper.ability.dto;

import com.pokerogue.helper.ability.domain.PokemonAbility;

public record PokemonAbilityResponse(Long id, String name, String description) {

    public static PokemonAbilityResponse from(PokemonAbility pokemonAbility) {
        return new PokemonAbilityResponse(pokemonAbility.getId(), pokemonAbility.getName(),
                pokemonAbility.getDescription());
    }
}
