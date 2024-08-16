package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.Getter;

@Getter
public class TrainerType {

    private final String id;
    private final String trainerName;
    private final List<String> trainerTypes;

    public TrainerType(String trainerType) {
        String[] trainerTypeInforms = trainerType.split(" / ");
        this.id = trainerTypeInforms[0];
        this.trainerName = trainerTypeInforms[1];
        this.trainerTypes = List.of(trainerTypeInforms[2].split(","));
    }
}
