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
            left join fetch pa.pokemonAbilityMappings pam
            left join fetch pam.pokemon p
            left join fetch p.pokemonTypeMappings ptm
            left join fetch ptm.pokemonType pt
            where pa.id = :id
            """)
    Optional<PokemonAbility> findByIdWithPokemonAndPokemonTypes(@NonNull @Param("id") Long id);
}
