package com.pokerogue.external.dto.pokemon;

import com.pokerogue.external.dto.InformationLink;
import java.util.List;

// https://pokeapi.co/api/v2/pokemon/{id}
// 해당 id의 포켓몬을 받아오는 Dto
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
