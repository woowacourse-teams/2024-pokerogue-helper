package com.pokerogue.helper.biome.domain;

import java.util.List;
import java.util.Map;

public class Biome {

    private final String name;
    private final Map<Tier, List<String>> pokemons;
    private final List<String> mainType;
    private final List<Trainer> trainer;
    private final List<Biome> nextBiomes;

    public Biome(String name, Map<Tier, List<String>> pokemons, List<String> mainType, List<Trainer> trainer,
                 List<Biome> nextBiomes) {
        this.name = name;
        this.pokemons = pokemons;
        this.mainType = mainType;
        this.trainer = trainer;
        this.nextBiomes = nextBiomes;
    }
}
