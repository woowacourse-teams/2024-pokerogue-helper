package com.pokerogue.helper.move.data;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum MoveCategory {

    STATUS("변화", "status"),
    SPECIAL("특수", "special"),
    PHYSICAL("물리", "physical"),
    ;

    private final String name;
    private final String engName;

    MoveCategory(String name, String engName) {
        this.name = name;
        this.engName = engName;
    }

    public static Optional<MoveCategory> findByEngName(String name) {
        return Arrays.stream(values())
                .filter(category -> category.hasSameEngName(name.toLowerCase()))
                .findAny();
    }

    private boolean hasSameEngName(String name) {
        return this.engName.equals(name);
    }

    public static MoveCategory convertFrom(String moveCategoryData) {
        return findByEngName(moveCategoryData)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));
    }
}
