package com.pokerogue.helper.pokemon2.dto;


import java.util.Objects;

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
