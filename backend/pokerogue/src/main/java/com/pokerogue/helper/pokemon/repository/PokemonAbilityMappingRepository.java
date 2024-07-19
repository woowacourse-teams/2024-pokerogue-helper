package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonAbilityMappingRepository extends JpaRepository<PokemonAbilityMapping, Long> {
}
