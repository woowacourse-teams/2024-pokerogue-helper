package com.pokerogue.external.dto.type;

import com.pokerogue.external.dto.DataUrl;
import java.util.List;

public record TypeMatchingResponse(DamageRelations damage_relations) {

    public List<DataUrl> getDoubleDamageTo() {
        return damage_relations.double_damage_to();
    }

    public List<DataUrl> getHalfDamageTo() {
        return damage_relations.half_damage_to();
    }

    public List<DataUrl> getNoDamageTo() {
        return damage_relations.no_damage_to();
    }
}
