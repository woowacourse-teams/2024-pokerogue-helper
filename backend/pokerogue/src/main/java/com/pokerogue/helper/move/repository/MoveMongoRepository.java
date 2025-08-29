package com.pokerogue.helper.move.repository;

import com.pokerogue.helper.move.data.Move;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveMongoRepository extends MongoRepository<Move, String> {

    Optional<Move> findByIndexAndLanguage(String index, String language);

    List<Move> findAllByLanguage(String language);
}
