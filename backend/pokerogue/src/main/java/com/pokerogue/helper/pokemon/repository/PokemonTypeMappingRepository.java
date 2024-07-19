package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonTypeMappingRepository extends JpaRepository<PokemonTypeMapping, Long> {
}
