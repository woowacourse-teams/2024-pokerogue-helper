package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.helper.pokemon.data.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PokemonRepository extends MongoRepository<Pokemon, String> {
}
