package com.pokerogue.helper.ability2.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class AbilityPokemonInfo {

    private final String id;
    private final String type1;
    private final String type2;
    private final String name;

    public AbilityPokemonInfo(String abilityPokemonInfo) {
        String[] abilityPokemonInfos = abilityPokemonInfo.split(" / ");
        this.id = abilityPokemonInfos[0];
        this.type1 = abilityPokemonInfos[1];
        this.type2 = abilityPokemonInfos[2];
        this.name = abilityPokemonInfos[3];
    }
}
