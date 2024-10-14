package com.pokerogue.helper.item.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "ability")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    private String id;

    @Id
    private String imageId;

    @Field("name")
    private String name;

    @Field("koName")
    private String koName;

    @Field("description")
    private String description;
}
