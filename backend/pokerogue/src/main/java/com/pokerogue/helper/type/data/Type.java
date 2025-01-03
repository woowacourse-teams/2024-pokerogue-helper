package com.pokerogue.helper.type.data;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum Type {

    GRASS("grass", "풀", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/grass"),
    POISON("poison", "독", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/poison"),
    FIRE("fire", "불꽃", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/fire"),
    WATER("water", "물", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/water"),
    ELECTRIC("electric", "전기", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/electric"),
    NORMAL("normal", "노말", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/normal"),
    FAIRY("fairy", "페어리", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/fairy"),
    BUG("bug", "벌레", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/bug"),
    DARK("dark", "악", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/dark"),
    DRAGON("dragon", "드래곤", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/dragon"),
    FIGHTING("fighting", "격투", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/fighting"),
    FLYING("flying", "비행", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/flying"),
    GHOST("ghost", "고스트", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/ghost"),
    GROUND("ground", "땅", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/ground"),
    ICE("ice", "얼음", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/ice"),
    ROCK("rock", "바위", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/rock"),
    PSYCHIC("psychic", "에스퍼", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/psychic"),
    STEEL("steel", "강철", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/steel"),
    STELLAR("stellar", "스텔라", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/stellar"),
    UNKNOWN("unknown", "언노운", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/unknown"),
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
