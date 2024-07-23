package com.pokerogue.helper.external.dto.pokemon;

import com.pokerogue.helper.external.dto.InformationLink;
import java.util.List;

public record PokemonDetail(
        String name,
        int weight,
        int height,
        InformationLink species,
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
