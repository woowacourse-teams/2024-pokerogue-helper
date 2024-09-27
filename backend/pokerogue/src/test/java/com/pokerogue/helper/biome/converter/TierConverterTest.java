package com.pokerogue.helper.biome.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.biome.data.Tier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TierConverterTest {

    @Test
    @DisplayName("들어온 값을 Tier Enum으로 변화한다.")
    void convert() {
        TierConverter tierConverter = new TierConverter();

        assertThat(tierConverter.convert("보스")).isEqualTo(Tier.BOSS);
    }
}
