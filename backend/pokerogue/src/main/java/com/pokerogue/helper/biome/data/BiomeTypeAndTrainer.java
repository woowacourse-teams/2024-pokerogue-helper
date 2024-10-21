package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.Getter;

@Getter
public class BiomeTypeAndTrainer {

    private final String id;
    private final String biomeName;
    private final List<String> biomeTypes;
    private final List<String> trainerNames;

    public BiomeTypeAndTrainer(String biomeTypeAndTrainer) {
        String[] biomeTypeAndTrainerInforms = biomeTypeAndTrainer.split(" / ");
        this.id = biomeTypeAndTrainerInforms[0];
        this.biomeName = biomeTypeAndTrainerInforms[1];
        this.biomeTypes = List.of(biomeTypeAndTrainerInforms[2].split(","));
        this.trainerNames = List.of(biomeTypeAndTrainerInforms[3].split(","));
    }
}
