package com.pokerogue.helper.external.dto.pokemon.species;

import com.pokerogue.helper.external.dto.Name;
import java.util.List;

// https://pokeapi.co/api/v2/pokemon-species/{id}
public record PokemonSpeciesResponse(String name, List<Name> names, Long id) {
}
