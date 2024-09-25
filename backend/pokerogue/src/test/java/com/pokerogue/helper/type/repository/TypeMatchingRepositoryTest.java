package com.pokerogue.helper.type.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.repository.MongoRepositoryTest;
import com.pokerogue.helper.type.data.Type;
import com.pokerogue.helper.type.entity.TypeMatching;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TypeMatchingRepositoryTest extends MongoRepositoryTest {

    @Autowired
    private TypeMatchingRepository typeMatchingRepository;

    @Test
    @DisplayName("모든 상성 정보가 정상적으로 Entity와 매핑되는지 확인한다.")
    void findAll_AndConvertToTypeMatching() {
        List<TypeMatching> typeMatchings = typeMatchingRepository.findAll();

        assertAll(
                () -> assertThat(typeMatchings).hasSize(361),
                () -> assertThat(typeMatchings.stream()
                        .allMatch(typeMatching -> typeMatching.getTo()
                                .getDeclaringClass()
                                .equals(Type.class)))
                        .isTrue(),
                () -> assertThat(typeMatchings.stream()
                        .allMatch(typeMatching -> typeMatching.getFrom()
                                .getDeclaringClass()
                                .equals(Type.class)))
                        .isTrue()
        );
    }
}
