package com.pokerogue.helper.pokemon.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelMove {

    @Field("level")
    private int level;

    @Field("moveId")
    private String moveId;
}
