package com.pokerogue.helper.move.data;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import software.amazon.awssdk.services.s3.endpoints.internal.Value.Str;

@Getter
@RequiredArgsConstructor
@Document(collection = "move")
public class Move {

    @Id
    private final String id;

    @Field("name")
    private final String name;

    @Field("koName")
    private final String koName;

    @Field("type")
    private final String type; // Todo enum 사용

    @Field("moveCategory")
    private final String moveCategory; // Todo

    @Field("moveTarget")
    private final String moveTarget; // Todo enum 만들기

    @Field("power")
    private final int power;

    @Field("accuracy")
    private final int accuracy;

    @Field("powerPoint")
    private final int powerPoint;

    @Field("effect")
    private final String effect;

    @Field("effectChance")
    private final int effectChance;

    @Field("priority")
    private final int priority;

    @Field("generation")
    private final int generation;

    @Field("released")
    private final String released;

    @Field("flags")
    private final List<String> flags; // Todo enum 사용

    @Field("pokemonIds")
    private final List<String> pokemonIds;
}
