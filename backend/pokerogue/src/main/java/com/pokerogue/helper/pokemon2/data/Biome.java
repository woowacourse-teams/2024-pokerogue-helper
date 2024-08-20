package com.pokerogue.helper.pokemon2.data;

import com.pokerogue.helper.pokemon2.Ability;
import java.util.Arrays;

public enum Biome {
    UNKNOWNLOCATION("unknownLocation", "기억할 수 없는 곳"),
    TOWN("TOWN", "마을"),
    PLAINS("PLAINS", "평야"),
    GRASS("GRASS", "풀숲"),
    TALL_GRASS("TALL_GRASS", "높은 풀숲"),
    METROPOLIS("METROPOLIS", "대도시"),
    FOREST("FOREST", "숲"),
    SEA("SEA", "바다"),
    SWAMP("SWAMP", "늪지"),
    BEACH("BEACH", "해변"),
    LAKE("LAKE", "호수"),
    SEABED("SEABED", "해저"),
    MOUNTAIN("MOUNTAIN", "산"),
    BADLANDS("BADLANDS", "악지"),
    CAVE("CAVE", "동굴"),
    DESERT("DESERT", "사막"),
    ICE_CAVE("ICE_CAVE", "얼음 동굴"),
    MEADOW("MEADOW", "목초지"),
    POWER_PLANT("POWER_PLANT", "발전소"),
    VOLCANO("VOLCANO", "화산"),
    GRAVEYARD("GRAVEYARD", "묘지"),
    DOJO("DOJO", "도장"),
    FACTORY("FACTORY", "공장"),
    RUINS("RUINS", "고대 유적"),
    WASTELAND("WASTELAND", "황무지"),
    ABYSS("ABYSS", "심연"),
    SPACE("SPACE", "우주"),
    CONSTRUCTION_SITE("CONSTRUCTION_SITE", "공사장"),
    JUNGLE("JUNGLE", "정글"),
    FAIRY_CAVE("FAIRY_CAVE", "페어리 동굴"),
    TEMPLE("TEMPLE", "사원"),
    SLUM("SLUM", "슬럼"),
    SNOWY_FOREST("SNOWY_FOREST", "눈덮인 숲"),
    ISLAND("ISLAND", "섬"),
    LABORATORY("LABORATORY", "연구소"),
    END("END", "???"),
    EMPTY("EMPTY", "EMPTY"),
    ;

    private final String id;
    private final String name;

    Biome(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Biome findById(String id) {
        return Arrays.stream(values())
                .filter(value -> value.getId().toLowerCase()
                        .replaceAll("-", "_")
                        .replaceAll(" ", "_")
                        .equals(id)
                )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바이옴 아이디입니다."));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
