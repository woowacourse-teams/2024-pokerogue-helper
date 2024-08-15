package com.pokerogue.helper.biome.domain;

import java.util.List;

public class Trainer {

    private final String name;
    private final List<String> trainerType;
    private final List<String> pokemons;

    public Trainer(String name, List<String> trainerType, List<String> pokemons) {
        this.name = name;
        this.trainerType = trainerType;
        this.pokemons = pokemons;
    }
}
