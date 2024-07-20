package com.pokerogue.helper.util;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.external.dto.Name;
import com.pokerogue.helper.external.dto.ability.AbilityResponse;
import com.pokerogue.helper.external.dto.ability.FlavorTextEntry;
import com.pokerogue.helper.external.dto.type.TypeResponse;
import com.pokerogue.helper.type.domain.PokemonType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DtoParser {

    public PokemonAbility getPokemonAbility(AbilityResponse abilityResponse) {
        List<Name> names = abilityResponse.names();
        String koName = "존재하지 않습니다";
        for (Name value : names) {
            if (value.language().name().equals("ko")) {
                koName = value.name();
            }
        }
        List<FlavorTextEntry> flavorTextEntries = abilityResponse.flavor_text_entries();
        String description = "존재하지 않습니다";
        for (int i = flavorTextEntries.size() - 1; i > -1; i--) {
            if (flavorTextEntries.get(i).language().name().equals("ko")) {
                description = flavorTextEntries.get(i).flavor_text();
            }
        }
        return new PokemonAbility(null, abilityResponse.name(), koName, description, "자세한 설명입니다.", new ArrayList<>());
    }

    public PokemonType getPokemonType(TypeResponse typeResponse) {
        String koName = "존재하지 않습니다";
        List<Name> names = typeResponse.names();
        for (Name value : names) {
            if (value.language().name().equals("ko")) {
                koName = value.name();
            }
        }
        return new PokemonType(null, typeResponse.name(), koName, "null");
    }
}
