package com.pokerogue.external.pokemon.dto.pokemon;

import com.pokerogue.external.pokemon.dto.DataUrl;

public record AbilityDataUrl(DataUrl ability) {

    public String getName() {
        return ability.name();
    }
}
