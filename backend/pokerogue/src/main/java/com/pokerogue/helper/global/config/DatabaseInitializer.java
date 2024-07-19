package com.pokerogue.helper.global.config;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import com.pokerogue.helper.pokemon.repository.PokemonAbilityMappingRepository;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.pokemon.repository.PokemonTypeMappingRepository;
import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.type.repository.PokemonTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
   추후 production에서는 제외되어야 함.
   ( @Profile 사용 )
 */
@Component
@Transactional
@RequiredArgsConstructor
public class DatabaseInitializer implements ApplicationRunner {

    private final PokemonRepository pokemonRepository;
    private final PokemonTypeRepository pokemonTypeRepository;
    private final PokemonAbilityRepository pokemonAbilityRepository;
    private final PokemonTypeMappingRepository pokemonTypeMappingRepository;
    private final PokemonAbilityMappingRepository pokemonAbilityMappingRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<Pokemon> pokemons = pokemonRepository.saveAll(List.of(
                new Pokemon(1L, "이상해씨", 69, 7, 45, 45, 55, 50, 60, 70, 500, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
                new Pokemon(4L, "파이리", 69, 7, 45, 45, 55, 50, 60, 70, 500, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"),
                new Pokemon(7L, "꼬부기", 69, 7, 45, 45, 55, 50, 60, 70, 500, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png"),
                new Pokemon(25L, "피카츄", 69, 7, 45, 45, 55, 50, 60, 70, 500, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"),
                new Pokemon(133L, "이브이", 69, 7, 45, 45, 55, 50, 60, 70, 500, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/133.png"),
                new Pokemon(152L, "치코리타", 69, 7, 45, 45, 55, 50, 60, 70, 500, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/152.png"),
                new Pokemon(393L, "펭도리", 69, 7, 45, 45, 55, 50, 60, 70, 500, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/393.png"),
                new Pokemon(702L, "데덴네", 69, 7, 45, 45, 55, 50, 60, 70, 500, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/702.png"),
                new Pokemon(54L, "고라파덕", 69, 7, 45, 45, 55, 50, 60, 70, 500, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/54.png")
        ));

        List<PokemonType> pokemonTypes = pokemonTypeRepository.saveAll(List.of(
                new PokemonType("grass", "풀", "grass dummy image"),
                new PokemonType("poison", "독", "poison dummy image"),
                new PokemonType("fire", "불", "fire dummy image"),
                new PokemonType("water", "물", "water dummy image"),
                new PokemonType("electric", "전기", "electric dummy image"),
                new PokemonType("normal", "물", "normal dummy image"),
                new PokemonType("fairy", "페어리", "fairy dummy image")
        ));

        List<PokemonAbility> pokemonAbilities = pokemonAbilityRepository.saveAll(List.of(
                new PokemonAbility("심록", "심록", "description", "detail description"),
                new PokemonAbility("엽록소", "엽록소", "description", "detail description"),
                new PokemonAbility("맹화", "맹화", "description", "detail description"),
                new PokemonAbility("선파워", "선파워", "description", "detail description"),
                new PokemonAbility("급류", "급류", "description", "detail description"),
                new PokemonAbility("심록", "심록", "description", "detail description"),
                new PokemonAbility("젖은접시", "젖은접시", "description", "detail description"),
                new PokemonAbility("정전기", "정전기", "description", "detail description"),
                new PokemonAbility("피뢰침", "피뢰침", "description", "detail description"),
                new PokemonAbility("도주", "도주", "description", "detail description"),
                new PokemonAbility("적응력", "적응력", "description", "detail description"),
                new PokemonAbility("위험예지", "위험예지", "description", "detail description"),
                new PokemonAbility("리프가드", "리프가드", "description", "detail description"),
                new PokemonAbility("위험예지", "위험예지", "description", "detail description"),
                new PokemonAbility("승기", "승기", "description", "detail description"),
                new PokemonAbility("볼주머니", "볼주머니", "description", "detail description"),
                new PokemonAbility("픽업", "픽업", "description", "detail description"),
                new PokemonAbility("습기", "습기", "description", "detail description"),
                new PokemonAbility("날씨부정", "날씨부정", "description", "detail description"),
                new PokemonAbility("쓱쓱", "쓱쓱", "description", "detail description")
        ));


        pokemonTypeMappingRepository.saveAll(List.of(
                new PokemonTypeMapping(pokemons.get(0), pokemonTypes.get(0)),
                new PokemonTypeMapping(pokemons.get(0), pokemonTypes.get(1)),
                new PokemonTypeMapping(pokemons.get(1), pokemonTypes.get(2)),
                new PokemonTypeMapping(pokemons.get(2), pokemonTypes.get(2)),
                new PokemonTypeMapping(pokemons.get(3), pokemonTypes.get(2)),
                new PokemonTypeMapping(pokemons.get(3), pokemonTypes.get(3)),
                new PokemonTypeMapping(pokemons.get(4), pokemonTypes.get(3)),
                new PokemonTypeMapping(pokemons.get(4), pokemonTypes.get(4)),
                new PokemonTypeMapping(pokemons.get(5), pokemonTypes.get(4)),
                new PokemonTypeMapping(pokemons.get(6), pokemonTypes.get(5)),
                new PokemonTypeMapping(pokemons.get(7), pokemonTypes.get(6)),
                new PokemonTypeMapping(pokemons.get(7), pokemonTypes.get(5)),
                new PokemonTypeMapping(pokemons.get(8), pokemonTypes.get(6))
        ));

        pokemonAbilityMappingRepository.saveAll(List.of(
                new PokemonAbilityMapping(pokemons.get(0), pokemonAbilities.get(0)),
                new PokemonAbilityMapping(pokemons.get(0), pokemonAbilities.get(1)),
                new PokemonAbilityMapping(pokemons.get(0), pokemonAbilities.get(16)),
                new PokemonAbilityMapping(pokemons.get(1), pokemonAbilities.get(2)),
                new PokemonAbilityMapping(pokemons.get(1), pokemonAbilities.get(17)),
                new PokemonAbilityMapping(pokemons.get(2), pokemonAbilities.get(18)),
                new PokemonAbilityMapping(pokemons.get(2), pokemonAbilities.get(3)),
                new PokemonAbilityMapping(pokemons.get(3), pokemonAbilities.get(4)),
                new PokemonAbilityMapping(pokemons.get(3), pokemonAbilities.get(5)),
                new PokemonAbilityMapping(pokemons.get(4), pokemonAbilities.get(6)),
                new PokemonAbilityMapping(pokemons.get(4), pokemonAbilities.get(7)),
                new PokemonAbilityMapping(pokemons.get(5), pokemonAbilities.get(8)),
                new PokemonAbilityMapping(pokemons.get(5), pokemonAbilities.get(15)),
                new PokemonAbilityMapping(pokemons.get(6), pokemonAbilities.get(9)),
                new PokemonAbilityMapping(pokemons.get(6), pokemonAbilities.get(14)),
                new PokemonAbilityMapping(pokemons.get(7), pokemonAbilities.get(10)),
                new PokemonAbilityMapping(pokemons.get(7), pokemonAbilities.get(11)),
                new PokemonAbilityMapping(pokemons.get(8), pokemonAbilities.get(12)),
                new PokemonAbilityMapping(pokemons.get(8), pokemonAbilities.get(13))
        ));
    }
}
