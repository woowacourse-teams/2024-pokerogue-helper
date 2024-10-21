package com.pokerogue.helper.pokemon.dto;

public record PokemonAbilityResponse(String id, String name, String description, Boolean passive, Boolean hidden) {

    public static PokemonAbilityResponse from(String name, String description, boolean passive, boolean hidden) {
        return new PokemonAbilityResponse(name, name, description, passive, hidden);
    }
}
