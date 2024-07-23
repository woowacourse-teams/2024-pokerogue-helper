package com.pokerogue.external.dto.type;

import com.pokerogue.external.dto.DataUrl;
import java.util.List;

public record DamageRelations(
        List<DataUrl> double_damage_to,
        List<DataUrl> half_damage_to,
        List<DataUrl> no_damage_to
) {
}
