package com.pokerogue.helper.global.config;

import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import org.springframework.stereotype.Component;

@Component
public class LanguageChecker {

    private final PokemonRepository pokemonRepository;

    public LanguageChecker(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public boolean existsByLanguage(String locale) {
        String language = locale.toLowerCase().substring(0, 2);
        return pokemonRepository.existsByLanguage(language);
    }
}
