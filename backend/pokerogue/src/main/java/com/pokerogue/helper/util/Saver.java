package com.pokerogue.helper.util;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.external.client.PokeClient;
import com.pokerogue.helper.external.dto.NameAndUrl;
import com.pokerogue.helper.external.dto.ability.AbilityResponse;
import com.pokerogue.helper.external.dto.pokemon.AbilitySummary;
import com.pokerogue.helper.external.dto.pokemon.PokemonDetails;
import com.pokerogue.helper.external.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.helper.external.dto.pokemon.TypeSummary;
import com.pokerogue.helper.external.dto.pokemon.species.PokemonNameAndDexNumber;
import com.pokerogue.helper.external.dto.pokemon.species.PokemonSpeciesResponse;
import com.pokerogue.helper.external.dto.type.TypeResponse;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import com.pokerogue.helper.pokemon.repository.PokemonAbilityMappingRepository;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.pokemon.repository.PokemonTypeMappingRepository;
import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.type.repository.PokemonTypeRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class Saver {
    private PokemonRepository pokemonRepository;
    private PokemonAbilityRepository pokemonAbilityRepository;
    private PokemonTypeRepository pokemonTypeRepository;
    private PokemonAbilityMappingRepository pokemonAbilityMappingRepository;
    private PokemonTypeMappingRepository pokemonTypeMappingRepository;
    private DtoParser dtoParser;
    private PokeClient pokeClient;

    public List<PokemonAbility> savePokemonAbilityList(List<AbilityResponse> abilityResponses) {
        List<PokemonAbility> pokemonAbilities = new ArrayList<>();
        for (AbilityResponse abilityResponse : abilityResponses) {
            PokemonAbility pokemonAbility = dtoParser.getPokemonAbility(abilityResponse);
            pokemonAbilities.add(pokemonAbility);
        }
        return pokemonAbilityRepository.saveAll(pokemonAbilities);
    }

    public List<PokemonType> savePokemonTypeList(List<TypeResponse> typeResponses) {
        List<PokemonType> pokemonTypes = new ArrayList<>();
        for (TypeResponse typeResponse : typeResponses) {
            PokemonType pokemonType = dtoParser.getPokemonType(typeResponse);
            pokemonTypes.add(pokemonType);
        }
        return pokemonTypeRepository.saveAll(pokemonTypes);
    }

    @Transactional
    public Pokemon savePokemon(PokemonSaveResponse pokemonSaveResponse) {
        PokemonDetails pokemonDetails = dtoParser.getPokemonDetails(pokemonSaveResponse);
        NameAndUrl species = pokemonDetails.species();
        PokemonNameAndDexNumber pokemonNameAndDexNumber = getPokemonNameAndDexNumber(
                getPokemonSpeciesResponse(species));
        Pokemon pokemon = new Pokemon(null, pokemonNameAndDexNumber.pokemonNumber(), pokemonDetails.name(),
                pokemonNameAndDexNumber.koName(), pokemonDetails.weight(),
                pokemonDetails.height(), pokemonDetails.hp(), pokemonDetails.speed(), pokemonDetails.attack(),
                pokemonDetails.defense(), pokemonDetails.specialAttack(), pokemonDetails.specialDefense(),
                pokemonDetails.totalStats(), "null", new ArrayList<>(), new ArrayList<>());
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        List<AbilitySummary> abilities = pokemonSaveResponse.abilities();
        for (AbilitySummary abilitySummary : abilities) {
            String name = abilitySummary.ability().name();
            PokemonAbility pokemonAbility = pokemonAbilityRepository.findByName(name)
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
            PokemonAbilityMapping pokemonAbilityMapping = new PokemonAbilityMapping(null, savedPokemon, pokemonAbility);
            savedPokemon.getPokemonAbilityMappings().add(pokemonAbilityMapping);
            pokemonAbility.getPokemonAbilityMappings().add(pokemonAbilityMapping);
            pokemonAbilityMappingRepository.save(pokemonAbilityMapping);
        }
        List<TypeSummary> types = pokemonSaveResponse.types();
        for (TypeSummary typeSummary : types) {
            String name = typeSummary.type().name();
            PokemonType pokemonType = pokemonTypeRepository.findByName(name)
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
            PokemonTypeMapping pokemonTypeMapping = new PokemonTypeMapping(null, savedPokemon, pokemonType);
            savedPokemon.getPokemonTypeMappings().add(pokemonTypeMapping);
            pokemonTypeMappingRepository.save(pokemonTypeMapping);
        }
        return savedPokemon;
    }

    private PokemonSpeciesResponse getPokemonSpeciesResponse(NameAndUrl species) {
        String[] split = species.url().split("/");
        return pokeClient.getPokemonSpeciesResponse(split[split.length - 1]);
    }

    private PokemonNameAndDexNumber getPokemonNameAndDexNumber(PokemonSpeciesResponse pokemonSpeciesResponse) {
        return dtoParser.getPokemonNameAndDexNumber(pokemonSpeciesResponse);
    }
}
