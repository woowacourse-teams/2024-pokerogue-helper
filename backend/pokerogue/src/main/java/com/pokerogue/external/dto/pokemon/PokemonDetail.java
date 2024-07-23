package com.pokerogue.external.dto.pokemon;

import com.pokerogue.external.dto.DataUrl;
import java.util.List;

public record PokemonDetail(
        String name,
        int weight,
        int height,
        DataUrl species,
        int hp,
        int attack,
        int defense,
        int speed,
        int specialAttack,
        int specialDefense,
        int totalStats,
        List<String> abilityNames,
        List<String> typeNames
) {
}
