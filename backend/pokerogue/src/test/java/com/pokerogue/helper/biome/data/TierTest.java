package com.pokerogue.helper.biome.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TierTest {

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "polla", "polaaaa"})
    @DisplayName("존재하지 않는 티어인 경우 에러를 발생한다.")
    void convertFrom_WhenNotExist(String inputTier) {
        assertThatThrownBy(() -> Tier.convertFrom(inputTier))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.TIER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("일치하는 티어를 찾는다.")
    void convertFrom() {
        assertThat(Tier.convertFrom("보스")).isEqualTo(Tier.BOSS);
    }
}
