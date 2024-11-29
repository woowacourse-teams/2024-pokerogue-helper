package com.pokerogue.helper.type.data;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum Type {

    GRASS("grass", "풀"),
    POISON("poison", "독"),
    FIRE("fire", "불꽃"),
    WATER("water", "물"),
    ELECTRIC("electric", "전기"),
    NORMAL("normal", "노말"),
    FAIRY("fairy", "페어리"),
    BUG("bug", "벌레"),
    DARK("dark", "악"),
    DRAGON("dragon", "드래곤"),
    FIGHTING("fighting", "격투"),
    FLYING("flying", "비행"),
    GHOST("ghost", "고스트"),
    GROUND("ground", "땅"),
    ICE("ice", "얼음"),
    ROCK("rock", "바위"),
    PSYCHIC("psychic", "에스퍼"),
    STEEL("steel", "강철"),
    STELLAR("stellar", "스텔라"),
    UNKNOWN("unknown", "언노운"),
    ;

    private final String name;
    private final String koName;

    Type(String name, String koName) {
        this.name = name;
        this.koName = koName;
    }

    public static Optional<Type> findByName(String name) {
        return Arrays.stream(values())
                .filter(type -> type.koName.equals(name))
                .findAny();
    }

    public static Optional<Type> findByEngName(String engName) {
        return Arrays.stream(values())
                .filter(type -> type.name.equals(engName))
                .findAny();
    }

    public static Type convertFrom(String typeData) {
        return findByEngName(typeData)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TYPE_NOT_FOUND));
    }
}
