package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NativePokemon {

    private final String tier; // Todo: enum 사용
    private final List<String> pokemonIds;
}
