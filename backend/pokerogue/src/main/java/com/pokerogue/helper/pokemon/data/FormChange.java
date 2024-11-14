package com.pokerogue.helper.pokemon.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FormChange {

    @Field("from")
    private String from;

    @Field("previousForm")
    private String previousForm;

    @Field("currentForm")
    private String currentForm;

    @Field("item")
    private EvolutionItem item;
}
