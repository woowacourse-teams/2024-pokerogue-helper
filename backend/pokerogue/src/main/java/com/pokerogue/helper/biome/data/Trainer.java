package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Trainer {

    private final String id; // Todo: 지우기
    private final String name;
    private final String image; // Todo: 지우기
    private final List<String> types; // Todo: List<Type>
    private final List<String> pokemonIds;
}
