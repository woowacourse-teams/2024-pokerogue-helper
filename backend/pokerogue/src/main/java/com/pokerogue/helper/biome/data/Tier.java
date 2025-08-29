package com.pokerogue.helper.biome.data;

import com.pokerogue.helper.global.config.LocaleContextHolder;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import java.util.Map;
import lombok.Getter;

@Getter
public enum Tier {

    COMMON(
            Map.of(
                    "en", "Common",
                    "ko", "보통")
            , 1
    ),
    UNCOMMON(
            Map.of(
                    "en", "Uncommon",
                    "ko", "드묾"
            ), 2
    ),
    RARE(
            Map.of(
                    "en", "Rare",
                    "ko", "레어"
            ), 3
    ),
    SUPER_RARE(
            Map.of(
                    "en", "Super Rare",
                    "ko", "슈퍼 레어"
            ), 4
    ),
    ULTRA_RARE(
            Map.of(
                    "en", "Ultra Rare",
                    "ko", "울트라 레어"
            ), 5
    ),
    BOSS(
            Map.of(
                    "en", "Boss",
                    "ko", "보스"
            ), 6
    ),
    BOSS_RARE(
            Map.of(
                    "en", "Rare Boss",
                    "ko", "레어 보스"
            ), 7
    ),
    BOSS_SUPER_RARE(
            Map.of(
                    "en", "Super Rare Boss",
                    "ko", "슈퍼 레어 보스"
            ), 8
    ),
    BOSS_ULTRA_RARE(
            Map.of(
                    "en", "Ultra Rare Boss",
                    "ko", "슈퍼 울트라 레어 보스"
            ), 9
    ),
    ;

    private final Map<String, String> names;
    private final int rarity;

    Tier(Map<String, String> names, int rarity) {
        this.names = names;
        this.rarity = rarity;
    }

    public String getName() {
        return names.get(LocaleContextHolder.getCurrentLocale());
    }

    public boolean isWild() {
        return !names.get("ko").contains("보스");
    }

    public boolean isBoss() {
        return names.get("ko").contains("보스");
    }

    public static Tier convertFrom(String tierData) {
        return getTierByKoName(tierData);
    }

    private static Tier getTierByKoName(String name) {
        return Arrays.stream(values())
                .filter(tier -> tier.names.get("ko").equals(name))
                .findFirst()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TIER_NOT_FOUND));
    }
}
