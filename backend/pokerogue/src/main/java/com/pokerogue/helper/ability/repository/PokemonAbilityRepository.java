package com.pokerogue.helper.ability.repository;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import io.micrometer.common.lang.NonNull;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PokemonAbilityRepository extends JpaRepository<PokemonAbility, Long> {

    @NonNull
    @Query("""
            select a from PokemonAbility a
            join fetch a.pokemonAbilityMappings m
            where a.id = :id
            """)
    Optional<PokemonAbility> findByIdWithPokemonMappings(@NonNull @Param("id") Long id);
}
