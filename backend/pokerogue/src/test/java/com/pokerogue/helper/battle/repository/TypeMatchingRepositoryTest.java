package com.pokerogue.helper.battle.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.pokerogue.helper.battle.data.TypeMatching;
import com.pokerogue.helper.type.data.Type;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

@DataMongoTest
@ActiveProfiles("local")
class TypeMatchingRepositoryTest {

    @Autowired
    private TypeMatchingRepository typeMatchingRepository;

    @Test
    @DisplayName("타입 간 상성 결과를 조회한다.")
    void findByFromAndTo() {
        // given
        String from = Type.BUG.getName();
        String to = Type.FIRE.getName();

        // when
        Optional<TypeMatching> typeMatching =typeMatchingRepository.findByFromAndTo(from, to);

        // then
        assertThat(typeMatching).isNotEmpty();
    }
}
