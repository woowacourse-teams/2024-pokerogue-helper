package com.pokerogue.helper.biome.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class BiomeTypeAndTrainer {

    private final String biomeName;
    private final List<String> biomeTypes;
    private final List<String> trainerNames;

    public BiomeTypeAndTrainer(String biomeTypeAndTrainer) {
        String[] biomeTypeAndTrainerInforms = biomeTypeAndTrainer.split(" / ");
        this.biomeName = biomeTypeAndTrainerInforms[0];
        this.biomeTypes = List.of(biomeTypeAndTrainerInforms[1].split(","));
        this.trainerNames = List.of(biomeTypeAndTrainerInforms[2].split(","));
    }
}
