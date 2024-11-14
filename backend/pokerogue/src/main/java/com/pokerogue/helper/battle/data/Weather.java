package com.pokerogue.helper.battle.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum Weather {

    NONE("none", "없음", "없음", List.of("없음")),
    SUNNY("sunny", "쾌청", "햇살이 강하다", List.of("불꽃 타입 기술의 위력이 1.5배가 된다", "물 타입 기술의 위력이 0.5배가 된다")),
    RAIN("rain", "비", "비가 계속 내리고 있다", List.of("물 타입 기술의 위력이 1.5배가 된다", "불꽃 타입 기술의 위력이 0.5배가 된다")),
    SANDSTORM("sandstorm", "모래바람", "모래바람이 세차게 분다",
            List.of("바위 또는 땅 또는 강철 타입 포켓몬이 아니면 매 턴마다 체력의 1/16의 데미지를 받는다", "바위 타입 포켓몬의 특수방어가 1.5배가 된다")),
    HAIL("hail", "싸라기눈", "싸라기눈이 계속 내리고 있다", List.of("얼음 타입 포켓몬이 아니면 매 턴마다 체력의 1/16의 데미지를 받는다")),
    SNOW("snow", "눈", "눈이 계속 내리고 있다", List.of("얼음 타입 포켓몬의 방어가 1.5배 올라간다")),
    FOG("fog", "안개", "발밑이 안개로 자욱하다", List.of("모든 기술의 명중률이 0.9배가 된다")),
    HEAVY_RAIN("heavy_rain", "강한 비", "강한 비가 계속 내리고 있다",
            List.of("불타입 기술은 모두 실패한다", "불꽃 타입 기술의 위력이 1.5배가 된다", "물 타입 기술의 위력이 1.5배가 된다")),
    HARSH_SUN("harsh_sun", "강한 쾌청", "햇살이 아주 강하다",
            List.of("물타입 기술은 모두 실패한다", "불꽃 타입 기술의 위력이 1.5배가 된다", "물 타입 기술의 위력이 0.5배가 된다")),
    STRONG_WINDS("strong_winds", "난기류", "수수께끼의 난기류가 강렬하게 불고 있다", List.of("비행 타입의 약점을 없애준다")),
    ;

    private final String id;
    private final String name;
    private final String description;
    private final List<String> effects;

    Weather(String id, String name, String description, List<String> effects) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.effects = effects;
    }

    public static Optional<Weather> findById(String id) {
        return Arrays.stream(values())
                .filter(weather -> weather.id.equals(id))
                .findAny();
    }
}
