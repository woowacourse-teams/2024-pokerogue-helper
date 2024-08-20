package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum MoveCategory {

    STATUS("status", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/move-category/status.png"),
    SPECIAL("special", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/move-category/special.png"),
    PHYSICAL("physical", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/move-category/physical.png"),
    ;

    private final String name;
    private final String image;

    MoveCategory(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public static MoveCategory findByName(String name) {
        return Arrays.stream(values())
                .filter(category -> category.hasSameName(name))
                .findAny()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));
    }

    private boolean hasSameName(String name) {
        return this.name.equals(name);
    }
}
