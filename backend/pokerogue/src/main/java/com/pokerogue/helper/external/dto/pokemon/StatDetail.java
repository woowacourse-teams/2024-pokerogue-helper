package com.pokerogue.helper.external.dto.pokemon;

import com.pokerogue.helper.external.dto.NameAndUrl;

public record StatDetail(int base_stat, NameAndUrl stat) {

    public String getStatName() {
        return stat.name();
    }
}
