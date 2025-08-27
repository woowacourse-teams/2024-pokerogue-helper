package com.pokerogue.helper.ability.repository;

import com.pokerogue.helper.ability.data.Ability;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbilityMongoRepository extends MongoRepository<Ability, String> {

    List<Ability> findAllByLanguage(String language);

    Optional<Ability> findByIndexAndLanguage(String index, String language);
}
