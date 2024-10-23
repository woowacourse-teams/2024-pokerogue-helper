package com.pokerogue.helper.pokemon.repository;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.pokerogue.helper.pokemon.data.Pokemon;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
public class PokemonInMemoryRepository {

    private final PokemonRepository pokemonRepository;
    private Map<String, Pokemon> pokemons;

    public PokemonInMemoryRepository(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @PostConstruct
    public void init() {
        refreshCache();
    }

    @Scheduled(cron = "0 0 5 * * *")
    public void refreshCache() {
        List<Pokemon> allPokemons = pokemonRepository.findAll();
        this.pokemons = allPokemons.stream()
                .collect(toMap(Pokemon::getId, identity()));
    }

    public List<Pokemon> findAll() {
        return pokemons.values()
                .stream()
                .toList();
    }

    public Optional<Pokemon> findById(String id) {
        return Optional.ofNullable(pokemons.get(id));
    }
}
