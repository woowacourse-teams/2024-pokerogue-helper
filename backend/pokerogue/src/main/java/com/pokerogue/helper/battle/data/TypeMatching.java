package com.pokerogue.helper.battle.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "typeMatching")
public class TypeMatching {

    @Id
    private String id;

    @Field("from")
    private String from; // Todo: enum

    @Field("to")
    private String to; // Todo: enum

    @Field("result")
    private double result;
}
