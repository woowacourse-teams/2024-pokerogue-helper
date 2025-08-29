package com.pokerogue.helper.type.data;

import com.pokerogue.helper.global.config.LocaleContextHolder;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum Type {

    GRASS(
            Map.of(
                    "en", "grass",
                    "ko", "풀"
            )
    ),
    POISON(
            Map.of(
                    "en", "poison",
                    "ko", "독"
            )
    ),
    FIRE(
            Map.of(
                    "en", "fire",
                    "ko", "불꽃"
            )
    ),
    WATER(
            Map.of(
                    "en", "water",
                    "ko", "물"
            )
    ),
    ELECTRIC(
            Map.of(
                    "en", "electric",
                    "ko", "전기"
            )
    ),
    NORMAL(
            Map.of(
                    "en", "normal",
                    "ko", "노말"
            )
    ),
    FAIRY(
            Map.of(
                    "en", "fairy",
                    "ko", "페어리"
            )
    ),
    BUG(
            Map.of(
                    "en", "bug",
                    "ko", "벌레"
            )
    ),
    DARK(
            Map.of(
                    "en", "dark",
                    "ko", "악"
            )
    ),
    DRAGON(
            Map.of(
                    "en", "dragon",
                    "ko", "드래곤"
            )
    ),
    FIGHTING(
            Map.of(
                    "en", "fighting",
                    "ko", "격투"
            )
    ),
    FLYING(
            Map.of(
                    "en", "flying",
                    "ko", "비행"
            )
    ),
    GHOST(
            Map.of(
                    "en", "ghost",
                    "ko", "고스트"
            )
    ),
    GROUND(
            Map.of(
                    "en", "ground",
                    "ko", "땅"
            )
    ),
    ICE(
            Map.of(
                    "en", "ice",
                    "ko", "얼음"
            )
    ),
    ROCK(
            Map.of(
                    "en", "rock",
                    "ko", "바위"
            )
    ),
    PSYCHIC(
            Map.of(
                    "en", "psychic",
                    "ko", "에스퍼"
            )
    ),
    STEEL(
            Map.of(
                    "en", "steel",
                    "ko", "강철"
            )
    ),
    STELLAR(
            Map.of(
                    "en", "stellar",
                    "ko", "스텔라"
            )
    ),
    UNKNOWN(
            Map.of(
                    "en", "unknown",
                    "ko", "언노운"
            )
    ),
    ;

    private final Map<String, String> names;

    Type(Map<String, String> names) {
        this.names = names;
    }

    public String getName() {
        return names.get(LocaleContextHolder.getCurrentLocale());
    }

    public static Type convertFrom(String typeData) {
        return findByEngName(typeData)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_NOT_FOUND));
    }

    private static Optional<Type> findByEngName(String engName) {
        return Arrays.stream(values())
                .filter(type -> type.names.get("en").equals(engName))
                .findAny();
    }
}
