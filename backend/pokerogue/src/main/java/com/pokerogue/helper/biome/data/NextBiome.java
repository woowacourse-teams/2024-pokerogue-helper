package com.pokerogue.helper.biome.data;

import lombok.Getter;

@Getter
public class NextBiome {

    private String name;
    private int percentage;

    public NextBiome() {

    }

    public NextBiome(String name, String percentage) {
        this.name = name;
        this.percentage = Integer.parseInt(percentage); // Todo
    }

    public String getPercent() {
        return String.valueOf(percentage);
    }
}
