package com.pokerogue.external.pokemon.dto.pokemon;

import com.pokerogue.external.pokemon.dto.DataUrl;

public record PokemonDetail(
        String name,
        double weight,
        double height,
        DataUrl species,
        int hp,
        int attack,
        int defense,
        int speed,
        int specialAttack,
        int specialDefense,
        int totalStats
) {
}
