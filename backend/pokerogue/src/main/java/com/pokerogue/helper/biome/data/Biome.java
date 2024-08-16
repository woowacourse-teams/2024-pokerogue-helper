package com.pokerogue.helper.biome.data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;

@Getter
public class Biome {

    private final String id;
    private final String name;
    private final String image;
    private final Map<Tier, List<String>> pokemons;
    private final List<String> mainTypes;
    private final List<Trainer> trainers;
    private final List<String> nextBiome;

    public Biome(
            String id,
            String name,
            String image,
            Map<Tier, List<String>> pokemons,
            List<String> mainTypes,
            List<Trainer> trainers,
            List<String> nextBiome
    ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.pokemons = pokemons;
        this.mainTypes = mainTypes;
        this.trainers = trainers;
        this.nextBiome = nextBiome;
    }

    public List<String> getTrainerTypes() {
        Set<String> trainerTypes = new HashSet<>();
        trainers.forEach(trainer -> trainerTypes.addAll(trainer.getTrainerTypes()));

        return trainerTypes.stream()
                .toList();
    }
}
