package com.pokerogue.external.pokemon.dto.type;

import com.pokerogue.external.pokemon.dto.Name;
import java.util.List;

// https://pokeapi.co/api/v2/type/{id}
// 해당 id의 타입을 받아오는 Dto
public record TypeResponse(String name, List<Name> names) {
}
