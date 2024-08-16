package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;

public enum PokemonType {
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

    private final String englishName;
    private final String koreanName;

    PokemonType(String englishName, String koreanName) {
        this.englishName = englishName;
        this.koreanName = koreanName;
    }

    public static String findEnglishNameByKoreanName(String koreanName) {
        return Arrays.stream(values())
                .filter(type -> type.hasSameKoreanName(koreanName))
                .findAny()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND))
                .englishName;
    }

    private boolean hasSameKoreanName(String koreanName) {
        return this.koreanName.equals(koreanName);
    }
}
