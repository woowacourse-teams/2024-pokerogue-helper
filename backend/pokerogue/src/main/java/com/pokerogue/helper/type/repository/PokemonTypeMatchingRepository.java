package com.pokerogue.helper.type.repository;

import com.pokerogue.helper.type.domain.PokemonTypeMatching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonTypeMatchingRepository extends JpaRepository<PokemonTypeMatching, Long> {
}
