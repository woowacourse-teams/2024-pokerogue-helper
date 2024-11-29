package com.pokerogue.helper.ability.dto;

import com.pokerogue.helper.global.config.ImageUrl;
import com.pokerogue.helper.type.data.Type;

public record AbilityTypeResponse(String typeLogo, String typeName) {

    public static AbilityTypeResponse from(Type type) {
        return new AbilityTypeResponse(ImageUrl.getTypeImage(type.getName()), type.getKoName());
    }
}
