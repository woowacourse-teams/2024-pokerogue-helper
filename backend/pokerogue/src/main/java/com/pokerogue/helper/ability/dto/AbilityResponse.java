package com.pokerogue.helper.ability.dto;

import com.pokerogue.helper.ability.domain.Ability;

public record AbilityResponse(Long id, String name, String shortDescription) {

    public static AbilityResponse from(Ability ability) {
        return new AbilityResponse(ability.getId(), ability.getName(), ability.getShortDescription());
    }
}
