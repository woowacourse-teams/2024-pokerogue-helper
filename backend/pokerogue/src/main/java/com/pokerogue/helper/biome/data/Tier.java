package com.pokerogue.helper.biome.data;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Tier {

    COMMON("보통", 1),
    UNCOMMON("드묾", 2),
    RARE("레어", 3),
    SUPER_RARE("슈퍼 레어", 4),
    ULTRA_RARE("울트라 레어", 5),
    BOSS("보스", 6),
    BOSS_RARE("레어 보스", 7),
    BOSS_SUPER_RARE("슈퍼 레어 보스", 8),
    BOSS_ULTRA_RARE("슈퍼 울트라 레어 보스", 9),
    ;

    private final String name;
    private final int rarity;

    Tier(String name, int rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    public boolean isWild() {
        return !name.contains("보스");
    }

    public boolean isBoss() {
        return name.contains("보스");
    }

    public static Tier convertFrom(String tierData) {
        return getTierByName(tierData);
    }

    private static Tier getTierByName(String name) {
        return Arrays.stream(values())
                .filter(tier -> tier.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.TIER_NOT_FOUND));
    }
}
