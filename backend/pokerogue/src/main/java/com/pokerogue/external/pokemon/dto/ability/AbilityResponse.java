package com.pokerogue.external.pokemon.dto.ability;

import com.pokerogue.external.pokemon.dto.Name;
import java.util.List;

// https://pokeapi.co/api/v2/ability/{id}
// 해당 id의 특성을 받아오는 Dto
public record AbilityResponse(
        List<FlavorTextEntry> flavor_text_entries,
        String name,
        List<Name> names,
        List<PokemonSummary> pokemon
) {
}
