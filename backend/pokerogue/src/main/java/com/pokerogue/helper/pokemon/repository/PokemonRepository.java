package com.pokerogue.helper.pokemon.repository;


import com.pokerogue.helper.pokemon.domain.Pokemon;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"pokemonAbilityMappings"})
    Optional<Pokemon> findById(@NonNull Long id);

    @Query("""
            select p from Pokemon p
            join fetch p.pokemonTypeMappings ptm
            join fetch ptm.pokemonType
            """)
    List<Pokemon> findAllWithTypes();
}
