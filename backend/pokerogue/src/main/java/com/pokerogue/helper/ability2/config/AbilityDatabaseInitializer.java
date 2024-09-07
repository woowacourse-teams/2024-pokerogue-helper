//package com.pokerogue.helper.ability2.config;
//
//import com.pokerogue.helper.ability2.data.Ability;
//import com.pokerogue.helper.ability2.data.AbilityInfo;
//import com.pokerogue.helper.ability2.repository.AbilityRepository;
//import com.pokerogue.helper.pokemon2.data.Pokemon;
//import com.pokerogue.helper.pokemon2.repository.Pokemon2Repository;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class AbilityDatabaseInitializer implements ApplicationRunner {
//
//    private final AbilityRepository abilityRepository;
//    private final Pokemon2Repository pokemon2Repository;
//
//    @Override
//    public void run(ApplicationArguments args) {
//        List<AbilityInfo> abilityInfos = new ArrayList<>();
//
//        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ability.txt");
//             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
//            while (true) {
//                String abilityInfo = bufferedReader.readLine();
//                if (abilityInfo == null) {
//                    break;
//                }
//                abilityInfos.add(new AbilityInfo(abilityInfo));
//            }
//        } catch (IOException e) {
//            log.error("error message : {}", e.getStackTrace()[0]);
//        }
//
//        abilityInfos.stream()
//                .map(abilityInfo -> new Ability(
//                        abilityInfo.getId(),
//                        abilityInfo.getName(),
//                        abilityInfo.getDescription(),
//                        getAbilityPokemon(abilityInfo.getPokemons()))
//                ).forEach(abilityRepository::save);
//    }
//
//    private List<Pokemon> getAbilityPokemon(List<String> pokemons) {
//        List<Pokemon> abilityPokemons = new ArrayList<>();
//        for (int i = 0; i < pokemons.size(); i++) {
//            String pokemonId = pokemons.get(i);
//
//            Pokemon pokemon = pokemon2Repository.findById(pokemonId)
//                    .orElseThrow();
//
//            abilityPokemons.add(pokemon);
//        }
//
//        return abilityPokemons;
//    }
//}
