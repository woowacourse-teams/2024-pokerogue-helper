package com.pokerogue.helper.biome.data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Biome {

    private final String id;
    private final String name;
    private final String image;
    private final Map<Tier, List<String>> pokemons;
    private final List<String> mainTypes;
    private final List<Trainer> trainers;
    private final List<String> nextBiome;

    public List<String> getTrainerTypes() {
        if (name.equals("없음")) {
            return List.of();
        }
        Set<String> trainerTypes = new HashSet<>();
        trainers.forEach(trainer -> trainerTypes.addAll(trainer.getTrainerTypes()));

        return trainerTypes.stream()
                .toList();
    }
}
