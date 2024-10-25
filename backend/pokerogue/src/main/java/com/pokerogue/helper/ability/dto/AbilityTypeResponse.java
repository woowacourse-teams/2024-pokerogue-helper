package com.pokerogue.helper.ability.dto;

import com.pokerogue.helper.type.data.Type;

public record AbilityTypeResponse(String typeLogo, String typeName) {

    public static AbilityTypeResponse from(Type type) {
        return new AbilityTypeResponse(type.getImage(), type.getKoName());
    }
}
