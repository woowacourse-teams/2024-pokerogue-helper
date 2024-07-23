package com.pokerogue.external.dto.type;

import com.pokerogue.external.dto.InformationLink;
import java.util.List;

public record TypeMatchingResponse(DamageRelations damage_relations) {

    public List<InformationLink> getDoubleDamageTo() {
        return damage_relations.double_damage_to();
    }

    public List<InformationLink> getHalfDamageTo() {
        return damage_relations.half_damage_to();
    }

    public List<InformationLink> getNoDamageTo() {
        return damage_relations.no_damage_to();
    }
}
