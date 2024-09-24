package com.pokerogue.helper.ability.repository;

import com.pokerogue.helper.ability.data.Ability;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AbilityRepository extends MongoRepository<Ability, String> {
}
