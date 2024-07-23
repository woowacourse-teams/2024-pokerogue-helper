package com.pokerogue.external.pokemon.dto.pokemon;

import com.pokerogue.external.pokemon.dto.DataUrl;

public record StatDetail(int base_stat, DataUrl stat) {

    public String getStatName() {
        return stat.name();
    }
}
