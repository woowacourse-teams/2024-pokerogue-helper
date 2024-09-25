package com.pokerogue.helper.battle.repository;

import com.pokerogue.helper.battle.data.TypeMatching;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeMatchingRepository extends MongoRepository<TypeMatching, String> {
}
