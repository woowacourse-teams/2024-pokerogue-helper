package com.pokerogue.helper.move.repository;

import com.pokerogue.helper.move.data.Move;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoveRepository extends MongoRepository<Move, String> {
}
