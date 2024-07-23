package com.pokerogue.helper.external.dto.pokemon;

import com.pokerogue.helper.external.dto.InformationLink;
import java.util.List;

// https://pokeapi.co/api/v2/pokemon/{id}
public record PokemonSaveResponse(
        int height,
        int weight,
        String name,
        List<AbilityInformationLink> abilities,
        List<TypeInformationLink> types,
        List<StatDetail> stats,
        InformationLink species
) {
}
