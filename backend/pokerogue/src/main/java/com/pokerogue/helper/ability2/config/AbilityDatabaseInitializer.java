package com.pokerogue.helper.ability2.config;

import com.pokerogue.helper.ability2.data.Ability;
import com.pokerogue.helper.ability2.data.AbilityInfo;
import com.pokerogue.helper.ability2.data.AbilityPokemon;
import com.pokerogue.helper.ability2.data.AbilityPokemonInfo;
import com.pokerogue.helper.ability2.repository.AbilityRepository;
import com.pokerogue.helper.biome.data.BiomePokemonType;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AbilityDatabaseInitializer implements ApplicationRunner {

    private final AbilityRepository abilityRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<AbilityInfo> abilityInfos = new ArrayList<>();
        List<AbilityPokemonInfo> abilityPokemonInfos = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ability.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (true) {
                String abilityInfo = bufferedReader.readLine();
                if (abilityInfo == null) {
                    break;
                }
                abilityInfos.add(new AbilityInfo(abilityInfo));
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getStackTrace()[0]);
        }

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ability-pokemon.txt");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (true) {
                String abilityPokemonInfo = bufferedReader.readLine();
                if (abilityPokemonInfo == null) {
                    break;
                }
                abilityPokemonInfos.add(new AbilityPokemonInfo(abilityPokemonInfo));
            }
        } catch (IOException e) {
            log.error("error message : {}", e.getStackTrace()[0]);
        }

        abilityInfos.stream()
                .map(abilityInfo -> new Ability(
                        abilityInfo.getId(),
                        abilityInfo.getName(),
                        abilityInfo.getDescription(),
                        getAbilityPokemon(abilityPokemonInfos, abilityInfo.getPokemons(), abilityInfo.getPokedexNumbers()))
                ).forEach(abilityRepository::save);
    }

    private List<AbilityPokemon> getAbilityPokemon(List<AbilityPokemonInfo> abilityPokemonInfos, List<String> pokemons, List<String> pokedexNumbers) {
        List<AbilityPokemon> abilityPokemons = new ArrayList<>();
        for (int i = 0; i < pokemons.size(); i++) {
            String pokemonId = pokemons.get(i);

            AbilityPokemonInfo abilityPokemon = abilityPokemonInfos.stream()
                    .filter(abilityPokemonInfo -> abilityPokemonInfo.getId().equals(pokemonId))
                    .findFirst()
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_NOT_FOUND));

            abilityPokemons.add(new AbilityPokemon(
                    pokemons.get(i),
                    Long.parseLong(pokedexNumbers.get(i)),
                    abilityPokemon.getName(),
                    abilityPokemon.getType1(),
                    abilityPokemon.getType2())
            );
        }

        return abilityPokemons;
    }
}
