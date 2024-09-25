package com.pokerogue.helper.move.data;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@AllArgsConstructor
@Document(collection = "move")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Move {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("koName")
    private String koName;

    @Field("type")
    private String type; // Todo enum 사용

    @Field("moveCategory")
    private String moveCategory; // Todo

    @Field("moveTarget")
    private String moveTarget; // Todo enum 만들기

    @Field("power")
    private int power;

    @Field("accuracy")
    private int accuracy;

    @Field("powerPoint")
    private int powerPoint;

    @Field("effect")
    private String effect;

    @Field("effectChance")
    private int effectChance;

    @Field("priority")
    private int priority;

    @Field("generation")
    private int generation;

    @Field("released")
    private String released;

    @Field("flags")
    private List<String> flags; // Todo enum 사용

    @Field("pokemonIds")
    private List<String> pokemonIds;
}
