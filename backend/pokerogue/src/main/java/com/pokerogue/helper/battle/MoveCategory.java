package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum MoveCategory {

    STATUS("변화", "status", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/move-category/status"),
    SPECIAL("특수", "special",
            "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/move-category/special"),
    PHYSICAL("물리", "physical",
            "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/move-category/physical"),
    ;

    private final String name;
    private final String engName;
    private final String image;

    MoveCategory(String name, String engName, String image) {
        this.name = name;
        this.engName = engName;
        this.image = image;
    }

    public static Optional<MoveCategory> findByEngName(String name) {
        return Arrays.stream(values())
                .filter(category -> category.hasSameEngName(name.toLowerCase()))
                .findAny();
    }

    private boolean hasSameEngName(String name) {
        return this.engName.equals(name);
    }

    public String getImage() {
        return image + ".png";
    }

    public static MoveCategory convertFrom(String moveCategoryData) {
        return findByEngName(moveCategoryData)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));
    }
}
