package com.pokerogue.helper.pokemon.repository;


import com.pokerogue.helper.pokemon.domain.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    @Query("""
            select p from Pokemon p
            join fetch  p.pokemonAbilityMappings pam
            join fetch pam.pokemonAbility pa
            where p.id = :id
            """)
    Optional<Pokemon> findDetailsById(@Param("id") Long id);

    @Query("""
            select p from Pokemon p
            join fetch p.pokemonTypeMappings ptm
            join fetch ptm.pokemonType
            order by p.id
            """)
    List<Pokemon> findAllWithTypes();
}
