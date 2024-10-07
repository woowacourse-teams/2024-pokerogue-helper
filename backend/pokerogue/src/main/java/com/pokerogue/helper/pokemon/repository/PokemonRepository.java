package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.helper.pokemon.data.Pokemon;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PokemonRepository extends MongoRepository<Pokemon, String> {

    List<Pokemon> findByPokedexNumber(int pokedexNumber);
}
