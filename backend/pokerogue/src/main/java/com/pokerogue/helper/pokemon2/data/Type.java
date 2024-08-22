package com.pokerogue.helper.pokemon2.data;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Type {

    UNKNOWN("UNKNOWN", "Unknown"),
    NORMAL("NORMAL", "노말"),
    FIGHTING("FIGHTING", "격투"),
    FLYING("FLYING", "비행"),
    POISON("POISON", "독"),
    GROUND("GROUND", "땅"),
    ROCK("ROCK", "바위"),
    BUG("BUG", "벌레"),
    GHOST("GHOST", "고스트"),
    STEEL("STEEL", "강철"),
    FIRE("FIRE", "불꽃"),
    WATER("WATER", "물"),
    GRASS("GRASS", "풀"),
    ELECTRIC("ELECTRIC", "전기"),
    PSYCHIC("PSYCHIC", "에스퍼"),
    ICE("ICE", "얼음"),
    DRAGON("DRAGON", "드래곤"),
    DARK("DARK", "악"),
    FAIRY("FAIRY", "페어리"),
    STELLAR("STELLAR", "스텔라"),
    EMPTY("", "");

    private final String id;
    private final String name;

    Type(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Type findById(String id) {
        return Arrays.stream(values())
                .filter(value -> value.getId()
                        .replace("-", "_")
                        .replace(" ", "_")
                        .toLowerCase()
                        .equals(id)
                )
                .findFirst()
                .orElse(EMPTY);
    }
}
