package com.pokerogue.helper.type.dto;

import com.pokerogue.helper.type.data.Type;

public record PokemonTypeResponse(String typeName, String typeLogo) {

    public static PokemonTypeResponse from(Type type) {
        return new PokemonTypeResponse(type.getKoName(), type.getImage());
    }
}
