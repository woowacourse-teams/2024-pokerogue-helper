package com.pokerogue.helper.biome.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NextBiome {

    @Field("name")
    private String name;

    @Field("percentage")
    private int percentage;
}
