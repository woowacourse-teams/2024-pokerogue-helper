package com.pokerogue.helper.pokemon.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Evolution {

    @Field("from")
    private String from;

    @Field("level")
    private String level;

    @Field("to")
    private String to;

    @Field("item")
    private String item;

    @Field("condition")
    private String condition;
}
