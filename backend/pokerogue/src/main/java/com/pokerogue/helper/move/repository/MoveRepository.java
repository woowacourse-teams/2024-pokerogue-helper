package com.pokerogue.helper.move.repository;

import com.pokerogue.helper.move.data.Move;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoveRepository extends MongoRepository<Move, String> {

    Optional<Move> findByIdAndLanguage(String id, String language);
}
