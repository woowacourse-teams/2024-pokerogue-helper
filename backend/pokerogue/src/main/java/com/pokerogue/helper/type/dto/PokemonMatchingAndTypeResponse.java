package com.pokerogue.helper.type.dto;

import java.util.List;

public record PokemonMatchingAndTypeResponse(List<PokemonMatchingResponse> matching, List<PokemonTypeResponse> images) {
}
