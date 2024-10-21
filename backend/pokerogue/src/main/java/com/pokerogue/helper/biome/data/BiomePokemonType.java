package com.pokerogue.helper.biome.data;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum BiomePokemonType {

    GRASS("풀", "grass"),
    POISON("독", "poison"),
    FIRE("불꽃", "fire"),
    WATER("물", "water"),
    ELECTRIC("전기", "electric"),
    NORMAL("노말", "normal"),
    FAIRY("페어리", "fairy"),
    BUG("벌레", "bug"),
    DARK("악", "dark"),
    DRAGON("드래곤", "dragon"),
    FIGHTING("격투", "fighting"),
    FLYING("비행", "flying"),
    GHOST("고스트", "ghost"),
    GROUND("땅", "ground"),
    ICE("얼음", "ice"),
    ROCK("바위", "rock"),
    PSYCHIC("에스퍼", "psychic"),
    STEEL("강철", "steel"),
    STELLAR("스텔라", "stellar"),
    UNKNOWN("없음", "unknown");

    private final String name;
    private final String id;

    BiomePokemonType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static BiomePokemonType getBiomePokemonTypeByName(String name) {
        return Arrays.stream(values())
                .filter(biomePokemonType -> biomePokemonType.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
    }

    public static BiomePokemonType getBiomePokemonTypeById(String id) {
        return Arrays.stream(values())
                .filter(biomePokemonType -> biomePokemonType.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
    }
}
