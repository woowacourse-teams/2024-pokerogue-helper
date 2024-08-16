package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.Getter;

@Getter
public class Trainer {

    private final String id;
    private final String name;
    private final String image;
    private final List<String> trainerTypes;
    private final List<String> pokemons;

    public Trainer(String id, String name, String image, List<String> trainerTypes, List<String> pokemons) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.trainerTypes = trainerTypes;
        this.pokemons = pokemons;
    }
}
