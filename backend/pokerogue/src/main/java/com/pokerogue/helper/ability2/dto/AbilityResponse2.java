package com.pokerogue.helper.ability2.dto;

import com.pokerogue.helper.ability2.data.Ability;

public record AbilityResponse2(String id, String koName, String description) {

    public static AbilityResponse2 from(Ability ability) {
        return new AbilityResponse2(ability.getId(), ability.getName(), ability.getDescription());
    }
}
