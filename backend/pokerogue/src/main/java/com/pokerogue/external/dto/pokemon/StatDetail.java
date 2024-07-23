package com.pokerogue.external.dto.pokemon;

import com.pokerogue.external.dto.DataUrl;

public record StatDetail(int base_stat, DataUrl stat) {

    public String getStatName() {
        return stat.name();
    }
}
