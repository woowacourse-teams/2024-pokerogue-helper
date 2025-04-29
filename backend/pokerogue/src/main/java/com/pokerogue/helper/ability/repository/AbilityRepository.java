package com.pokerogue.helper.ability.repository;

import com.pokerogue.helper.ability.data.Ability;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AbilityRepository extends MongoRepository<Ability, String> {

    Optional<Ability> findByIdAndLanguage(String id, String language);
}
