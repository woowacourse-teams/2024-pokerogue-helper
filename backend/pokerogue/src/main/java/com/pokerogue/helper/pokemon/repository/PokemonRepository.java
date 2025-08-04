package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.helper.pokemon.data.Pokemon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PokemonRepository extends MongoRepository<Pokemon, String> {

    List<Pokemon> findByPokedexNumber(int pokedexNumber);

    List<Pokemon> findByEggMoveIdsContains(String eggMoveIds);

    List<Pokemon> findByLevelMovesMoveId(String moveId);

    Optional<Pokemon> findByIndexAndLanguage(String index, String language);

    List<Pokemon> findByPokedexNumberAndLanguage(Integer pokedexNumber, String language);
}
