package com.pokerogue.helper.type.dto;

import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.type.domain.PokemonTypeMatching;
import java.util.List;

public record PokemonMatchingAndTypeResponse(
        List<PokemonMatchingResponse> matching,
        List<PokemonTypeResponse> images
) {
}
