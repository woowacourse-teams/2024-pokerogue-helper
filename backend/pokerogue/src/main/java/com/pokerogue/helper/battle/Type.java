package com.pokerogue.helper.battle;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import java.util.Optional;

public enum Type {

    GRASS("grass", "풀", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/grass-1"),
    POISON("poison", "독", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/poison-1"),
    FIRE("fire", "불꽃", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/fire-1"),
    WATER("water", "물", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/water-1"),
    ELECTRIC("electric", "전기", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/electric-1"),
    NORMAL("normal", "노말", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/normal-1"),
    FAIRY("fairy", "페어리", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/fairy-1"),
    BUG("bug", "벌레", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/bug-1"),
    DARK("dark", "악", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/dark-1"),
    DRAGON("dragon", "드래곤", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/dragon-1"),
    FIGHTING("fighting", "격투", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/fighting-1"),
    FLYING("flying", "비행", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/flying-1"),
    GHOST("ghost", "고스트", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/ghost-1"),
    GROUND("ground", "땅", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/ground-1"),
    ICE("ice", "얼음", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/ice-1"),
    ROCK("rock", "바위", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/rock-1"),
    PSYCHIC("psychic", "에스퍼", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/psychic-1"),
    STEEL("steel", "강철", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/steel-1"),
    STELLAR("stellar", "스텔라", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/stellar-1"),
    UNKNOWN("unknown", "언노운", "https://dl70s9ccojnge.cloudfront.net/pokerogue-helper/pokerogue/type/unknown-1"),
    ;

    private final String engName;
    private final String name;
    private final String image;

    Type(String engName, String name, String image) {
        this.engName = engName;
        this.name = name;
        this.image = image;
    }

    public static Optional<Type> findByName(String name) {
        return Arrays.stream(values())
                .filter(type -> type.name.equals(name))
                .findAny();
    }

    public static Optional<Type> findByEngName(String engName) {
        return Arrays.stream(values())
                .filter(type -> type.engName.equals(engName))
                .findAny();
    }

    public String getImage() {
        return image + ".png";
    }

    public String getName() {
        return name;
    }

    public String getEngName() {
        return engName;
    }
}
