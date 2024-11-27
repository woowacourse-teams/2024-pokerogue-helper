package com.pokerogue.helper.type.data;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum Type {

    GRASS("grass", "풀", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/grass"),
    POISON("poison", "독", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/poison"),
    FIRE("fire", "불꽃", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/fire"),
    WATER("water", "물", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/water"),
    ELECTRIC("electric", "전기", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/electric"),
    NORMAL("normal", "노말", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/normal"),
    FAIRY("fairy", "페어리", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/fairy"),
    BUG("bug", "벌레", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/bug"),
    DARK("dark", "악", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/dark"),
    DRAGON("dragon", "드래곤", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/dragon"),
    FIGHTING("fighting", "격투", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/fighting"),
    FLYING("flying", "비행", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/flying"),
    GHOST("ghost", "고스트", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/ghost"),
    GROUND("ground", "땅", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/ground"),
    ICE("ice", "얼음", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/ice"),
    ROCK("rock", "바위", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/rock"),
    PSYCHIC("psychic", "에스퍼", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/psychic"),
    STEEL("steel", "강철", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/steel"),
    STELLAR("stellar", "스텔라", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/stellar"),
    UNKNOWN("unknown", "언노운", "https://d11z3l5940xyw9.cloudfront.net/pokerogue/type/unknown"),
    ;

    private final String name;
    private final String koName;
    private final String image;

    Type(String name, String koName, String image) {
        this.name = name;
        this.koName = koName;
        this.image = image;
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
