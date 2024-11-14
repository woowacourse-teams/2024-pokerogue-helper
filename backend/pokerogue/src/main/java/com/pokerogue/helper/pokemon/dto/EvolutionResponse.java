package com.pokerogue.helper.pokemon.dto;


import com.pokerogue.helper.pokemon.config.ImageUrl;
import com.pokerogue.helper.pokemon.data.Evolution;
import com.pokerogue.helper.pokemon.data.Pokemon;

public record EvolutionResponse(
        String id,
        String name,
        Integer level,
        Integer depth,
        String item,
        String condition,
        String image
) {

    public static EvolutionResponse from(Pokemon pokemon, Evolution evolution, Integer depth) {
        return new EvolutionResponse(
                pokemon.getId(),
                pokemon.getKoName(),
                evolution.getLevel(),
                depth,
                evolution.getItem(),
                "",
                ImageUrl.getPokemonImage(pokemon.getImageId())
        );
    }
}
