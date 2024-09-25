package com.pokerogue.helper.pokemon.dto;

public record PokemonBiomeResponse(String id, String name, String image) {
    public static final PokemonBiomeResponse EMPTY = new PokemonBiomeResponse("", "", "");
}
