package com.pokerogue.helper.external.dto.ability;

import com.pokerogue.helper.external.dto.Name;
import java.util.List;

// https://pokeapi.co/api/v2/ability/{id}
public record AbilityResponse(List<FlavorTextEntry> flavor_text_entries, String name, List<Name> names,
                              List<PokemonSummary> pokemon) {
}
