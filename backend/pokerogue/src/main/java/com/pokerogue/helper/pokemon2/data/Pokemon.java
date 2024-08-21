package com.pokerogue.helper.pokemon2.data;

import java.util.List;

public record Pokemon(
        String id,
        String speciesId,
        String speciesName,
        String formName,
        String koName,
        String type1,
        String type2,
        String ability1,
        String ability2,
        String abilityHidden,
        String abilityPassive,
        Integer generation,
        Boolean legendary,
        Boolean subLegendary,
        Boolean mythical,
        Boolean canChangeForm,
        List<String> evolutionLevel,
        Integer baseTotal,
        Integer hp,
        Integer attack,
        Integer defense,
        Integer specialAttack,
        Integer specialDefense,
        Integer speed,
        Double height,
        Double weight,
        List<String> eggMoves,
        List<String> moves,
        List<String> biomes
) {

}
