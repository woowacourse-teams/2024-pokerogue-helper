package com.pokerogue.helper.biome.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class BiomeLink {

    private final String currentBiome;
    private final List<String> nextBiomes;

    public BiomeLink(String biomeLink) {
        String[] biomeLinkInforms = biomeLink.split(" / ");
        this.currentBiome = biomeLinkInforms[0];
        this.nextBiomes = List.of(biomeLinkInforms[1].split(","));
    }
}
