package com.pokerogue.external.pokemon.dto.pokemon;

import com.pokerogue.external.pokemon.dto.DataUrl;

public record TypeDataUrl(DataUrl type) {

    public String getName() {
        return type.name();
    }
}