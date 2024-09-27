package com.pokerogue.helper.biome.data;

import com.pokerogue.helper.type.data.Type;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Trainer {

    private final String id; // Todo: 지우기
    private final String name;
    private final String image; // Todo: 지우기
    private final List<Type> types;
    private final List<String> pokemonIds;
}
