package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.helper.global.config.LocaleContextHolder;
import com.pokerogue.helper.pokemon.data.Pokemon;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PokemonRepository {

    private final PokemonMongoRepository pokemonMongoRepository;

    public List<Pokemon> findByEggMoveIdsContains(String eggMoveIds) {
        return pokemonMongoRepository.findByLanguageAndEggMoveIdsContains(LocaleContextHolder.getCurrentLocale(), eggMoveIds);
    }

    public List<Pokemon> findByLevelMovesMoveId(String moveId) {
        return pokemonMongoRepository.findByLevelMovesMoveIdAndLanguage(moveId, LocaleContextHolder.getCurrentLocale());
    }

    public Optional<Pokemon> findByIndex(String index) {
        return pokemonMongoRepository.findByIndexAndLanguage(index, LocaleContextHolder.getCurrentLocale());
    }

    public List<Pokemon> findByPokedexNumber(Integer pokedexNumber) {
        return pokemonMongoRepository.findByPokedexNumberAndLanguage(pokedexNumber, LocaleContextHolder.getCurrentLocale());
    }
}
