package com.pokerogue.helper.ability.repository;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonAbilityRepository extends JpaRepository<PokemonAbility, Long> {

    Optional<PokemonAbility> findByName(String name);
}
