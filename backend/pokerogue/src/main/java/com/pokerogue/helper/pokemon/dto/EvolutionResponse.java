package com.pokerogue.helper.pokemon.dto;


import com.pokerogue.helper.pokemon.data.Evolution;

public record EvolutionResponse(
        String id,
        String name,
        Integer level,
        Integer depth,
        String item,
        String condition,
        String image
) {

    public static EvolutionResponse from(Evolution evolution, int depth, String pokemonId, String imageId) {
        return new EvolutionResponse(
                pokemonId,
                evolution.getTo(),
                evolution.getLevel(),
                depth,
                evolution.getItem(),
                evolution.getCondition(),
                imageId
        );
    }
}
