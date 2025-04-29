package com.pokerogue.helper.biome.data;

import com.pokerogue.helper.global.config.LanguageSetter;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Tier {

    COMMON("Common", "보통", 1),
    UNCOMMON("Uncommon", "드묾", 2),
    RARE("Rare", "레어", 3),
    SUPER_RARE("Super Rare", "슈퍼 레어", 4),
    ULTRA_RARE("Ultra Rare", "울트라 레어", 5),
    BOSS("Boss", "보스", 6),
    BOSS_RARE("Rare Boss", "레어 보스", 7),
    BOSS_SUPER_RARE("Super Rare Boss", "슈퍼 레어 보스", 8),
    BOSS_ULTRA_RARE("Ultra Rare Boss", "슈퍼 울트라 레어 보스", 9),
    ;

    private final String name;
    private final String koName;
    private final int rarity;

    Tier(String name, String koName, int rarity) {
        this.name = name;
        this.koName = koName;
        this.rarity = rarity;
    }

    public String getName() {
        if (LanguageSetter.isKorean()) {
            return koName;
        }
        return name;
    }

    public boolean isWild() {
        return !koName.contains("보스");
    }

    public boolean isBoss() {
        return koName.contains("보스");
    }

    public static Tier convertFrom(String tierData) {
        return getTierByKoName(tierData);
    }

    private static Tier getTierByKoName(String name) {
        return Arrays.stream(values())
                .filter(tier -> tier.koName.equals(name))
                .findFirst()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TIER_NOT_FOUND));
    }
}
