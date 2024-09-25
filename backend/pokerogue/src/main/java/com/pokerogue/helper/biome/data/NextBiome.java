package com.pokerogue.helper.biome.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NextBiome {

    private String name;
    private int percentage;

    public NextBiome(String name, String percentage) {
        this.name = name;
        this.percentage = Integer.parseInt(percentage);
    }

    public String getPercent() {
        return String.valueOf(percentage);
    }
}
