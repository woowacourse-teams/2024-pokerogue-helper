package com.pokerogue.helper.battle;

import java.util.List;

public record BattlePokemon(String id, List<Type> pokemonTypes) {

    public boolean hasSameType(Type type) {
        return this.pokemonTypes.stream()
                .anyMatch(pokemonType -> pokemonType.equals(type));
    }
}
