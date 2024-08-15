package com.pokerogue.helper.battle;

import java.util.List;

public record BattlePokemon(
        String id,
        Integer pokedexNumber,
        String name,
        List<String> moveIds
) {
}
