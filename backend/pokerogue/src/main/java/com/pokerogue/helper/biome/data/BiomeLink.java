package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.Getter;

@Getter
public class BiomeLink {

    private final String id;
    private final String currentBiome;
    private final List<String> nextBiomes;

    public BiomeLink(String biomeLink) {
        String[] biomeLinkInforms = biomeLink.split(" / ");
        this.id = biomeLinkInforms[0];
        this.currentBiome = biomeLinkInforms[1];
        this.nextBiomes = List.of(biomeLinkInforms[2].split(","));
    }
}
