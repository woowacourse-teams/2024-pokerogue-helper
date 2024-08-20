package com.pokerogue.helper.pokemon2.dto;

import jakarta.annotation.Nullable;

@Nullable
public record PokemonAbilityResponse(String id, String name, String description, Boolean passive, Boolean hidden) {

    public static PokemonAbilityResponse from(String ability) {
        return new PokemonAbilityResponse("","","",false,false);
    }
}
