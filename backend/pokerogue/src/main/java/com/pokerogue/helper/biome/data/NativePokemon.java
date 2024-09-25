package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NativePokemon {

    @Field("tier")
    private String tier; // Todo: enum 사용

    @Field("pokemonIds")
    private List<String> pokemonIds;
}
