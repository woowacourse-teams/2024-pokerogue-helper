package com.pokerogue.helper.pokemon2.data;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum MoveCategory {

    STATUS("status", "변화", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/move-category/status.png"),
    SPECIAL("special", "특수",
            "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/move-category/special.png"),
    PHYSICAL("physical", "물리",
            "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/move-category/physical.png"),
    ;

    private final String id;
    private final String name;
    private final String image;

    MoveCategory(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public static MoveCategory findById(String id) {
        return Arrays.stream(values())
                .filter(category -> category.id.equals(id))
                .findAny()
                .orElseThrow();
    }
}
