package com.pokerogue.helper.type.repository;

import com.pokerogue.helper.type.collection.TypeMatching;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeMatchingRepository extends MongoRepository<TypeMatching, String> {

    Optional<TypeMatching> findByFromAndTo(String from, String to);
}
