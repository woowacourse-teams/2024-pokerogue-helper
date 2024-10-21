package com.pokerogue.helper.type.repository;

import com.pokerogue.helper.type.domain.PokemonType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonTypeRepository extends JpaRepository<PokemonType, Long> {
    Optional<PokemonType> findByKoName(String koName);
}
