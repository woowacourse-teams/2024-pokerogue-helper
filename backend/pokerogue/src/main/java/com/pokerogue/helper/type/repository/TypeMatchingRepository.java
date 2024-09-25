package com.pokerogue.helper.type.repository;

import com.pokerogue.helper.type.entity.TypeMatching;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeMatchingRepository extends MongoRepository<TypeMatching, String> {
}
