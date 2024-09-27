package com.pokerogue.helper.biome.data;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NativePokemon {

    private static final String BOSS = "보스";

    @Field("tier")
    private Tier tier;

    @Field("pokemonIds")
    private List<String> pokemonIds;

    public boolean isWild() {
        return tier.isWild();
    }

    public boolean isBoss() {
        return tier.isBoss();
    }
}
