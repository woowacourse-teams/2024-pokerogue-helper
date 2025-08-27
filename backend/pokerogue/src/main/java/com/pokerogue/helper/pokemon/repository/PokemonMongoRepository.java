package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.helper.pokemon.data.Pokemon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonMongoRepository extends MongoRepository<Pokemon, String> {

    List<Pokemon> findByLanguageAndEggMoveIdsContains(String language, String eggMoveIds);

    List<Pokemon> findByLevelMovesMoveIdAndLanguage(String moveId, String language);

    Optional<Pokemon> findByIndexAndLanguage(String index, String language);

    List<Pokemon> findByPokedexNumberAndLanguage(Integer pokedexNumber, String language);

    boolean existsByLanguage(String language);
}
