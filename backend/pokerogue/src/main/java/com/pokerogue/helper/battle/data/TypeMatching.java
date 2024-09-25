package com.pokerogue.helper.battle.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@RequiredArgsConstructor
@Document(collection = "typeMatching")
public class TypeMatching {

    @Id
    private final String id;

    @Field("from")
    private final String from; // Todo: enum

    @Field("to")
    private final String to; // Todo: enum

    @Field("result")
    private final double result;
}
