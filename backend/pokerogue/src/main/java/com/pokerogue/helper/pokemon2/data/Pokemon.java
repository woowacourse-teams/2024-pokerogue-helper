package com.pokerogue.helper.pokemon2.data;

import com.pokerogue.helper.battle.Type;
import java.util.List;

public record Pokemon(
        String id,
        String speciesId,
        String koName,
        String speciesName,
        String formName,
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

    public boolean hasSameType(Type moveType) {
        String engName = moveType.getEngName();
        return (engName.equals(type1) || engName.equals(type2));
    }
}
