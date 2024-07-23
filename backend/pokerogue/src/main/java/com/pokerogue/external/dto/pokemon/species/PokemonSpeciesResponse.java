package com.pokerogue.external.dto.pokemon.species;

import com.pokerogue.external.dto.Name;
import java.util.List;

// https://pokeapi.co/api/v2/pokemon-species/{id}
// 해당 id의 포켓몬 종 특성을 받아오는 Dto
public record PokemonSpeciesResponse(String name, List<Name> names, Long id) {
}
