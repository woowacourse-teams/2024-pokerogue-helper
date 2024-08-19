package com.pokerogue.helper.biome.data;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum BiomePokemonType {

    GRASS("풀"),
    POISON("독"),
    FIRE("불꽃"),
    WATER("물"),
    ELECTRIC("전기"),
    NORMAL("노말"),
    FAIRY("페어리"),
    BUG("벌레"),
    DARK("악"),
    DRAGON("드래곤"),
    FIGHTING("격투"),
    FLYING("비행"),
    GHOST("고스트"),
    GROUND("땅"),
    ICE("얼음"),
    ROCK("바위"),
    PSYCHIC("에스퍼"),
    STEEL("강철"),
    STELLAR("스텔라"),
    UNKNOWN("없음");

    private final String name;

    BiomePokemonType(String name) {
        this.name = name;
    }

    public static BiomePokemonType getBiomePokemonTypeByName(String name) {
        return Arrays.stream(values())
                .filter(biomePokemonType -> biomePokemonType.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
    }
}
