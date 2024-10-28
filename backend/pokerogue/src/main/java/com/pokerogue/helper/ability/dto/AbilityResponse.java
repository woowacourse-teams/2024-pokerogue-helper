package com.pokerogue.helper.ability.dto;

import com.pokerogue.helper.ability.data.Ability;

public record AbilityResponse(String id, String koName, String description) {

    public static AbilityResponse from(Ability ability) {
        return new AbilityResponse(ability.getId(), ability.getKoName(), ability.getDescription());
    }
}
