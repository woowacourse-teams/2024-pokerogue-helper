package com.pokerogue.helper.pokemon.dto;


public record EvolutionResponse(
        String id,
        String name,
        Integer level,
        Integer depth,
        String item,
        String condition,
        String image
) {

}
