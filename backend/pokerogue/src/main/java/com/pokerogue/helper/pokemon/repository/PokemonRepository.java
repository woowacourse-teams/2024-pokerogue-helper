package com.pokerogue.helper.pokemon.repository;


import com.pokerogue.helper.pokemon.domain.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
}
