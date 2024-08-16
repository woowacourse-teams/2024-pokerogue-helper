package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.Getter;

@Getter
public class Trainer {

    private final String id;
    private final String name;
    private final List<String> trainerTypes;
    private final List<String> pokemons;

    public Trainer(String id, String name, List<String> trainerTypes, List<String> pokemons) {
        this.id = id;
        this.name = name;
        this.trainerTypes = trainerTypes;
        this.pokemons = pokemons;
    }
}
