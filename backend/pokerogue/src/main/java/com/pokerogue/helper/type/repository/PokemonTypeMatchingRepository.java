package com.pokerogue.helper.type.repository;

import com.pokerogue.helper.type.domain.PokemonTypeMatching;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface PokemonTypeMatchingRepository extends JpaRepository<PokemonTypeMatching, Long> {

    @Override
    @NonNull
    @Query("""
            select ptm from PokemonTypeMatching ptm
            join fetch ptm.toType tt
            join fetch ptm.fromType ft
            """)
    List<PokemonTypeMatching> findAll();
}
