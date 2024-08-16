package com.pokerogue.helper.biome.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class Trainer {

    private final String name;
    private final List<String> trainerTypes;
    private final List<String> pokemons;

    public Trainer(String name, List<String> trainerTypes, List<String> pokemons) {
        this.name = name;
        this.trainerTypes = trainerTypes;
        this.pokemons = pokemons;
    }
}
