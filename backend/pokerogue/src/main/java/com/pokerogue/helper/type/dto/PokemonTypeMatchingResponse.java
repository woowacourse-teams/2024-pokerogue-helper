package com.pokerogue.helper.type.dto;

import java.util.List;

public record PokemonTypeMatchingResponse(List<TypeMatchingResponse> matching, List<PokemonTypeResponse> images) {
}
