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
        String generation,
        String legendary,
        String subLegendary,
        String mythical,
        String canChangeForm,
        List<String> evolutionLevel,
        String baseTotal,
        String baseStats,
        String height,
        String weight,
        List<String> eggMoves,
        List<String> moves,
        List<String> biomes
) {

}
