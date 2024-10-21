package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Trainer {

    private final String id;
    private final String name;
    private final String image;
    private final List<String> trainerTypes;
    private final List<String> pokemons;
}
