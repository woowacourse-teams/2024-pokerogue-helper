package com.pokerogue.helper.pokemon2.data;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum EvolutionItem {
    NONE("NONE"),
    LINKING_CORD("연결의끈"),
    SUN_STONE("태양의돌"),
    MOON_STONE("달의돌"),
    LEAF_STONE("리프의돌"),
    FIRE_STONE("불꽃의돌"),
    WATER_STONE("물의돌"),
    THUNDER_STONE("천둥의돌"),
    ICE_STONE("얼음의돌"),
    DUSK_STONE("어둠의돌"),
    DAWN_STONE("각성의돌"),
    SHINY_STONE("빛의돌"),
    CRACKED_POT("깨진포트"),
    SWEET_APPLE("달콤한사과"),
    TART_APPLE("새콤한사과"),
    STRAWBERRY_SWEET("딸기사탕공예"),
    UNREMARKABLE_TEACUP("범작찻잔"),
    CHIPPED_POT("이빠진포트"),
    BLACK_AUGURITE("검은휘석"),
    GALARICA_CUFF("가라두구팔찌"),
    GALARICA_WREATH("가라두구머리장식"),
    PEAT_BLOCK("피트블록"),
    AUSPICIOUS_ARMOR("축복받은갑옷"),
    MALICIOUS_ARMOR("저주받은갑옷"),
    MASTERPIECE_TEACUP("걸작찻잔"),
    METAL_ALLOY("복합금속"),
    SCROLL_OF_DARKNESS("악의족자"),
    SCROLL_OF_WATERS("물의족자"),
    SYRUPY_APPLE("꿀맛사과"),
    ;

    private final String koName;

    EvolutionItem(String koName) {
        this.koName = koName;
    }

    public static EvolutionItem findById(String id) {
        return Arrays.stream(values())
                .filter(value -> value.name()
                        .toLowerCase()
                        .equals(id))
                .findAny()
                .orElseThrow();
    }
}
