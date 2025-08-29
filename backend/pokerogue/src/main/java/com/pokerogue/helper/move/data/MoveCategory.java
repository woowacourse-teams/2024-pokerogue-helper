package com.pokerogue.helper.move.data;

import com.pokerogue.helper.global.config.LocaleContextHolder;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum MoveCategory {

    STATUS(
            Map.of(
                    "ko", "변화",
                    "en", "status"
            )
    ),
    SPECIAL(
            Map.of(
                    "ko", "특수",
                    "en", "special"
            )
    ),
    PHYSICAL(
            Map.of(
                    "ko", "물리",
                    "en", "physical"
            )
    ),
    ;

    private final Map<String, String> names;

    MoveCategory(Map<String, String> names) {
        this.names = names;
    }

    public String getName() {
        return names.get(LocaleContextHolder.getCurrentLocale());
    }

    public String getEngName() {
        return names.get("en");
    }

    public static MoveCategory convertFrom(String moveCategoryData) {
        return findByEngName(moveCategoryData)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.MOVE_CATEGORY_NOT_FOUND));
    }

    private static Optional<MoveCategory> findByEngName(String name) {
        return Arrays.stream(values())
                .filter(category -> category.hasSameEngName(name.toLowerCase()))
                .findAny();
    }

    private boolean hasSameEngName(String name) {
        return this.names.get("en").equals(name);
    }
}
