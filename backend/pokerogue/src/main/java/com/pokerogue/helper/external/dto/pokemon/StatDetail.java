package com.pokerogue.helper.external.dto.pokemon;

import com.pokerogue.helper.external.dto.InformationLink;

public record StatDetail(int base_stat, InformationLink stat) {

    public String getStatName() {
        return stat.name();
    }
}
