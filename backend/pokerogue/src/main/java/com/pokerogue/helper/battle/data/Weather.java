package com.pokerogue.helper.battle.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Getter;

@Getter
public enum Weather {

    NONE(
            "none",
            "None",
            "없음",
            "None",
            "없음",
            List.of("None"),
            List.of("없음")
    ),
    SUNNY(
            "sunny",
            "Sunny",
            "쾌청",
            "The sunlight is strong.",
            "햇살이 강하다",
            List.of(
                    "The power of Fire-type moves is increased by 1.5 times",
                    "The power of Water-type moves is reduced by 0.5 times"
            ),
            List.of("불꽃 타입 기술의 위력이 1.5배가 된다", "물 타입 기술의 위력이 0.5배가 된다")
    ),
    RAIN(
            "rain",
            "Rain",
            "비",
            "The downpour continues.",
            "비가 계속 내리고 있다",
            List.of(
                    "The power of Water-type moves is increased by 1.5 times",
                    "The power of Fire-type moves is reduced by 0.5 times"
            ),
            List.of("물 타입 기술의 위력이 1.5배가 된다", "불꽃 타입 기술의 위력이 0.5배가 된다")
    ),
    SANDSTORM(
            "sandstorm",
            "Sandstorm",
            "모래바람",
            "The sandstorm rages.",
            "모래바람이 세차게 분다",
            List.of(
                    "Non-Rock, Ground, or Steel-type Pokémon take damage equal to 1/16 of their HP each turn",
                    "The Special Defense of Rock-type Pokémon is increased by 1.5 times"
            ),
            List.of(
                    "바위 또는 땅 또는 강철 타입 포켓몬이 아니면 매 턴마다 체력의 1/16의 데미지를 받는다",
                    "바위 타입 포켓몬의 특수방어가 1.5배가 된다"
            )
    ),
    HAIL(
            "hail",
            "Hail",
            "싸라기눈",
            "Hail continues to fall.",
            "싸라기눈이 계속 내리고 있다",
            List.of("Non-Ice-type Pokémon take damage equal to 1/16 of their HP each turn"),
            List.of("얼음 타입 포켓몬이 아니면 매 턴마다 체력의 1/16의 데미지를 받는다")
    ),
    SNOW(
            "snow",
            "Snow",
            "눈",
            "The snow is falling down.",
            "눈이 계속 내리고 있다",
            List.of("The Defense of Ice-type Pokémon is increased by 1.5 times"),
            List.of("얼음 타입 포켓몬의 방어가 1.5배 올라간다")
    ),
    FOG(
            "fog",
            "Fog",
            "안개",
            "The fog continues.",
            "발밑이 안개로 자욱하다",
            List.of("The accuracy of all moves is reduced to 0.9 times"),
            List.of("모든 기술의 명중률이 0.9배가 된다")
    ),
    HEAVY_RAIN(
            "heavy_rain",
            "Heavy rain",
            "강한 비",
            "The heavy downpour continues.",
            "강한 비가 계속 내리고 있다",
            List.of(
                    "Fire-type moves always fail",
                    "The power of Fire-type moves is increased by 0.5 times",
                    "The power of Water-type moves is increased by 1.5 times"
            ),
            List.of("불타입 기술은 모두 실패한다", "불꽃 타입 기술의 위력이 0.5배가 된다", "물 타입 기술의 위력이 1.5배가 된다")
    ),
    HARSH_SUN(
            "harsh_sun",
            "Harsh sun",
            "강한 쾌청",
            "The sun is scorching hot.",
            "햇살이 아주 강하다",
            List.of(
                    "Water-type moves always fail",
                    "The power of Fire-type moves is increased by 1.5 times",
                    "The power of Water-type moves is reduced by 0.5 times"
            ),
            List.of("물타입 기술은 모두 실패한다", "불꽃 타입 기술의 위력이 1.5배가 된다", "물 타입 기술의 위력이 0.5배가 된다")
    ),
    STRONG_WINDS(
            "strong_winds",
            "Strong winds",
            "난기류",
            "The wind blows intensely.",
            "수수께끼의 난기류가 강렬하게 불고 있다",
            List.of("Eliminates the weaknesses of Flying-type Pokémon"),
            List.of("비행 타입의 약점을 없애준다")
    ),
    ;

    private final String id;
    private final String name;
    private final String koName;
    private final String description;
    private final String koDescription;
    private final List<String> effects;
    private final List<String> koEffect;

    Weather(String id, String name, String koName, String description, String koDescription, List<String> effects, List<String> koEffect) {
        this.id = id;
        this.name = name;
        this.koName = koName;
        this.description = description;
        this.koDescription = koDescription;
        this.effects = effects;
        this.koEffect = koEffect;
    }

    public static Optional<Weather> findById(String id) {
        return Arrays.stream(values())
                .filter(weather -> weather.id.equals(id))
                .findAny();
    }
}
