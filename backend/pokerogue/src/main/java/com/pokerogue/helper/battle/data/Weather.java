package com.pokerogue.helper.battle.data;

import com.pokerogue.helper.global.config.LocaleContextHolder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum Weather {

    NONE(
            "none",
            Map.of(
                    "en", "None",
                    "ko", "없음"
            ),
            Map.of(
                    "en", "None",
                    "ko", "없음"
            ),
            Map.of(
                    "en", List.of("None"),
                    "ko", List.of("없음")
            )
    ),
    SUNNY(
            "sunny",
            Map.of(

                    "en", "Sunny",
                    "ko", "쾌청"
            ),
            Map.of(
                    "en", "The sunlight is strong.",
                    "ko", "햇살이 강하다"
            ),
            Map.of(
                    "en", List.of(
                            "The power of Fire-type moves is increased by 1.5 times",
                            "The power of Water-type moves is reduced by 0.5 times"
                    ),
                    "ko", List.of("불꽃 타입 기술의 위력이 1.5배가 된다", "물 타입 기술의 위력이 0.5배가 된다")
            )
    ),
    RAIN(
            "rain",
            Map.of(
                    "en", "Rain",
                    "ko", "비"
            ),
            Map.of(
                    "en", "The downpour continues.",
                    "ko", "비가 계속 내리고 있다"
            ),
            Map.of(
                    "en", List.of(
                            "The power of Water-type moves is increased by 1.5 times",
                            "The power of Fire-type moves is reduced by 0.5 times"
                    ),
                    "ko", List.of("물 타입 기술의 위력이 1.5배가 된다", "불꽃 타입 기술의 위력이 0.5배가 된다")
            )
    ),
    SANDSTORM(
            "sandstorm",
            Map.of(
                    "en", "Sandstorm",
                    "ko", "모래바람"
            ),
            Map.of(
                    "en", "The sandstorm rages.",
                    "ko", "모래바람이 세차게 분다"
            ),
            Map.of(
                    "en", List.of(
                            "Non-Rock, Ground, or Steel-type Pokémon take damage equal to 1/16 of their HP each turn",
                            "The Special Defense of Rock-type Pokémon is increased by 1.5 times"
                    ),
                    "ko", List.of(
                            "바위 또는 땅 또는 강철 타입 포켓몬이 아니면 매 턴마다 체력의 1/16의 데미지를 받는다",
                            "바위 타입 포켓몬의 특수방어가 1.5배가 된다"
                    )
            )
    ),
    HAIL(
            "hail",
            Map.of(
                    "en", "Hail",
                    "ko", "싸라기눈"
            ),
            Map.of(
                    "en", "Hail continues to fall.",
                    "ko", "싸라기눈이 계속 내리고 있다"
            ),
            Map.of(
                    "en", List.of("Non-Ice-type Pokémon take damage equal to 1/16 of their HP each turn"),
                    "ko", List.of("얼음 타입 포켓몬이 아니면 매 턴마다 체력의 1/16의 데미지를 받는다")
            )
    ),
    SNOW(
            "snow",
            Map.of(
                    "en", "Snow",
                    "ko", "눈"
            ),
            Map.of(
                    "en", "The snow is falling down.",
                    "ko", "눈이 계속 내리고 있다"
            ),
            Map.of(
                    "en", List.of("The Defense of Ice-type Pokémon is increased by 1.5 times"),
                    "ko", List.of("얼음 타입 포켓몬의 방어가 1.5배 올라간다")
            )
    ),
    FOG(
            "fog",
            Map.of(
                    "en", "Fog",
                    "ko", "안개"
            ),
            Map.of(
                    "en", "The fog continues.",
                    "ko", "발밑이 안개로 자욱하다"
            ),
            Map.of(
                    "en", List.of("The accuracy of all moves is reduced to 0.9 times"),
                    "ko", List.of("모든 기술의 명중률이 0.9배가 된다")
            )
    ),
    HEAVY_RAIN(
            "heavy_rain",
            Map.of(
                    "en", "Heavy rain",
                    "ko", "강한 비"
            ),
            Map.of(
                    "en", "The heavy downpour continues.",
                    "ko", "강한 비가 계속 내리고 있다"
            ),
            Map.of(
                    "en", List.of(
                            "Fire-type moves always fail",
                            "The power of Fire-type moves is increased by 0.5 times",
                            "The power of Water-type moves is increased by 1.5 times"
                    ),
                    "ko", List.of("불타입 기술은 모두 실패한다", "불꽃 타입 기술의 위력이 0.5배가 된다", "물 타입 기술의 위력이 1.5배가 된다")
            )
    ),
    HARSH_SUN(
            "harsh_sun",
            Map.of(
                    "en", "Harsh sun",
                    "ko", "강한 쾌청"
            ),
            Map.of(
                    "en", "The sun is scorching hot.",
                    "ko", "햇살이 아주 강하다"
            ),
            Map.of(
                    "en", List.of(
                            "Water-type moves always fail",
                            "The power of Fire-type moves is increased by 1.5 times",
                            "The power of Water-type moves is reduced by 0.5 times"
                    ),
                    "ko", List.of("물타입 기술은 모두 실패한다", "불꽃 타입 기술의 위력이 1.5배가 된다", "물 타입 기술의 위력이 0.5배가 된다")
            )
    ),
    STRONG_WINDS(
            "strong_winds",
            Map.of(
                    "en", "Strong winds",
                    "ko", "난기류"
            ),
            Map.of(
                    "en", "The wind blows intensely.",
                    "ko", "수수께끼의 난기류가 강렬하게 불고 있다"
            ),
            Map.of(
                    "en", List.of("Eliminates the weaknesses of Flying-type Pokémon"),
                    "ko", List.of("비행 타입의 약점을 없애준다")
            )
    ),
    ;

    private final String id;
    private final Map<String, String> names;
    private final Map<String, String> descriptions;
    private final Map<String, List<String>> effects;

    Weather(String id, Map<String, String> names, Map<String, String> descriptions, Map<String, List<String>> effects) {
        this.id = id;
        this.names = names;
        this.descriptions = descriptions;
        this.effects = effects;
    }

    public static Optional<Weather> findById(String id) {
        return Arrays.stream(values())
                .filter(weather -> weather.id.equals(id))
                .findAny();
    }

    public String getName() {
        return names.get(LocaleContextHolder.getCurrentLocale());
    }

    public String getDescription() {
        return descriptions.get(LocaleContextHolder.getCurrentLocale());
    }

    public List<String> getEffect() {
        return effects.get(LocaleContextHolder.getCurrentLocale());
    }
}
