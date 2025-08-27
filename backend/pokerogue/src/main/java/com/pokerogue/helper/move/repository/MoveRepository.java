package com.pokerogue.helper.move.repository;

import com.pokerogue.helper.global.config.LocaleContextHolder;
import com.pokerogue.helper.move.data.Move;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MoveRepository {

    private final MoveMongoRepository moveMongoRepository;

    public Optional<Move> findByIndex(String index) {
        return moveMongoRepository.findByIndexAndLanguage(index, LocaleContextHolder.getCurrentLocale());
    }

    public List<Move> findAll() {
        return moveMongoRepository.findAllByLanguage(LocaleContextHolder.getCurrentLocale());
    }
}
