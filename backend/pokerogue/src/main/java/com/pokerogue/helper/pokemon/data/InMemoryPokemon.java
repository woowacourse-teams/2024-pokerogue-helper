package com.pokerogue.helper.pokemon.data;

import com.pokerogue.helper.type.data.Type;
import java.util.List;

public record InMemoryPokemon(
        String id,
        String speciesId,
        String koName,
        String speciesName,
        String formName,
        String firstType,
        String secondType,
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
        List<String> technicalMachineMoves,
        List<String> biomes
) {

    public boolean hasSameType(Type moveType) {
        String engName = moveType.getName();
        return (engName.equals(firstType) || engName.equals(secondType));
    }
}
