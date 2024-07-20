package com.pokerogue.helper.external.dto.pokemon;

import com.pokerogue.helper.external.dto.NameAndUrl;
import java.util.List;

// https://pokeapi.co/api/v2/pokemon/{id}
public record PokemonSaveResponse(int height, int weight, String name, List<AbilitySummary> abilities, List<TypeSummary> types, List<StatDetail> stats, NameAndUrl species) {
}
