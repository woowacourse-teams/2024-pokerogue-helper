package com.pokerogue.helper.move.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.pokerogue.environment.repository.MongoRepositoryTest;
import com.pokerogue.helper.move.data.Move;
import com.pokerogue.helper.move.data.MoveCategory;
import com.pokerogue.helper.move.data.MoveFlag;
import com.pokerogue.helper.move.data.MoveTarget;
import com.pokerogue.helper.type.data.Type;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MoveRepositoryTest extends MongoRepositoryTest {

    @Autowired
    private MoveRepository moveRepository;

    @Test
    @DisplayName("모든 기술 정보가 정상적으로 매핑되는지 확인한다.")
    void findAll_AndConvertToMove() {
        List<Move> moves = moveRepository.findAll();

        assertAll(
                () -> assertThat(moves).hasSize(919),
                () -> assertThat(moves.stream()
                        .allMatch(move -> move.getType()
                                .getDeclaringClass()
                                .equals(Type.class)))
                        .isTrue(),
                () -> assertThat(moves.stream()
                        .allMatch(move -> move.getMoveTarget()
                                .getDeclaringClass()
                                .equals(MoveTarget.class)))
                        .isTrue(),
                () -> assertThat(moves.stream()
                        .allMatch(move -> move.getMoveCategory()
                                .getDeclaringClass()
                                .equals(MoveCategory.class)))
                        .isTrue(),
                () -> assertThat(moves.stream()
                        .flatMap(move -> move.getFlags().stream())
                        .allMatch(flag -> flag.getDeclaringClass()
                                .equals(MoveFlag.class)))
                        .isTrue()
        );
    }
}
