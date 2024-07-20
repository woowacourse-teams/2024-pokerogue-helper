package com.pokerogue.helper.external.dto.type;

import com.pokerogue.helper.external.dto.Name;
import java.util.List;

// https://pokeapi.co/api/v2/type/{id}
public record TypeResponse(String name, List<Name> names) {
}
