package com.pokerogue.external.dto.type;

import com.pokerogue.external.dto.InformationLink;
import java.util.List;

public record DamageRelations(
        List<InformationLink> double_damage_to,
        List<InformationLink> half_damage_to,
        List<InformationLink> no_damage_to
) {
}
