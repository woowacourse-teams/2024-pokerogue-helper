package com.pokerogue.helper.battle;

import java.util.List;

public record BattlePokemon(
        String id,
        List<PokemonType> pokemonTypes,
        String name
) {
}
