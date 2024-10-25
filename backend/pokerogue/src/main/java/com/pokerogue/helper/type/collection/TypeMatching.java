package com.pokerogue.helper.type.collection;

import com.pokerogue.helper.type.data.Type;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "typeMatching")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TypeMatching {

    @Id
    private String id;

    @Field("from")
    private Type from;

    @Field("to")
    private Type to;

    @Field("result")
    private int result;
}
