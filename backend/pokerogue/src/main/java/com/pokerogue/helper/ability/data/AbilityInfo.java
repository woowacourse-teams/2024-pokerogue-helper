package com.pokerogue.helper.ability.data;

import java.util.List;
import lombok.Getter;

@Getter
public class AbilityInfo {

    private final String id;
    private final String name;
    private final String description;
    private final List<String> pokemons;
    private final List<String> pokedexNumbers;

    public AbilityInfo(String abilityInfo) {
        String[] abilityInfos = abilityInfo.split(" / ");
        this.id = abilityInfos[0];
        this.name = abilityInfos[1];
        this.description = abilityInfos[2];
        this.pokemons = List.of(abilityInfos[4].split(" , "));
        this.pokedexNumbers = List.of(abilityInfos[5].split(" , "));
    }
}
