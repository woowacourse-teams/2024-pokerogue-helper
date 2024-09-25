package com.pokerogue.helper.biome.data;

import lombok.Getter;

@Getter
public enum Tier {

    COMMON("보통"),
    UNCOMMON("드묾"),
    RARE("레어"),
    SUPER_RARE("슈퍼 레어"),
    ULTRA_RARE("울트라 레어"),
    BOSS("보스"),
    BOSS_RARE("레어 보스"),
    BOSS_SUPER_RARE("슈퍼 레어 보스"),
    BOSS_ULTRA_RARE("슈퍼 울트라 레어 보스")
    ;

    private final String name;

    Tier(String name) {
        this.name = name;
    }

    public static boolean isWild(String tier) {
        return !tier.contains("보스");
    }

    public static boolean isBoss(String tier) {
        return tier.contains("보스");
    }
}
