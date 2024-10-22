package com.pokerogue.helper.biome.data;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class BiomeLink {

    private final String id;
    private final String currentBiome;
    private final List<NextBiome> nextBiomes;

    public BiomeLink(String biomeLink) {
        String[] biomeLinkInforms = biomeLink.split(" / ");
        this.id = biomeLinkInforms[0];
        this.currentBiome = biomeLinkInforms[1];
        this.nextBiomes = Arrays.stream(biomeLinkInforms[2].split(","))
                .map(s -> new NextBiome(s.split("~")[0], s.split("~")[1]))
                .toList();
    }
}
