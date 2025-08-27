package com.pokerogue.helper.global.config;

import com.pokerogue.helper.pokemon.repository.PokemonMongoRepository;
import org.springframework.stereotype.Component;

@Component
public class LanguageChecker {

    private final PokemonMongoRepository pokemonMongoRepository;

    public LanguageChecker(PokemonMongoRepository pokemonMongoRepository) {
        this.pokemonMongoRepository = pokemonMongoRepository;
    }

    public boolean existsByLanguage(String locale) {
        String language = locale.toLowerCase().substring(0, 2);
        return pokemonMongoRepository.existsByLanguage(language);
    }
}
