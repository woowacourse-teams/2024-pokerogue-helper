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
            select pa from PokemonAbility pa
            join fetch pa.pokemonAbilityMappings pam
            join fetch pam.pokemon p
            join fetch p.pokemonTypeMappings ptm
            join fetch ptm.pokemonType pt
            where pa.id = :id
            """)
    Optional<PokemonAbility> findByIdWithPokemonAndPokemonTypes(@NonNull @Param("id") Long id);

    Optional<PokemonAbility> findByName(String name);
}
