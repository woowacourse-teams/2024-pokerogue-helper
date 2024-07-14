package com.pokerogue.helper.ability.repository;

import com.pokerogue.helper.ability.domain.Ability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbilityRepository extends JpaRepository<Ability, Long> {
}
