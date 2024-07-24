package com.pokerogue.external.pokemon.dto.pokemon;

import com.pokerogue.external.pokemon.dto.DataUrl;
import java.util.List;

// https://pokeapi.co/api/v2/pokemon/{id}
// 해당 id의 포켓몬을 받아오는 Dto
public record PokemonSaveResponse(
        int height,
        int weight,
        String name,
        List<AbilityDataUrl> abilities,
        List<TypeDataUrl> types,
        List<StatDetail> stats,
        DataUrl species
) {
}
