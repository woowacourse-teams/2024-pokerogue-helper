package com.pokerogue.helper.util;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.external.client.PokeClient;
import com.pokerogue.helper.external.dto.CountResponse;
import com.pokerogue.helper.external.dto.ListResponse;
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

    public List<PokemonAbility> savePokemonAbilityList() {
        ListResponse abilityList = getAbilityList();
        List<AbilityResponse> abilityResponses = getAbilityResponses(abilityList);
        List<PokemonAbility> pokemonAbilities = getPokemonAbilities(abilityResponses);

        return pokemonAbilityRepository.saveAll(pokemonAbilities);
    }

    private ListResponse getAbilityList() {
        CountResponse abilityListSize = pokeClient.getAbilityListSize();

        return pokeClient.getAbilityList(String.valueOf(abilityListSize.count()));
    }

    private List<AbilityResponse> getAbilityResponses(ListResponse abilityList) {
        List<AbilityResponse> abilityResponses = new ArrayList<>();
        for (NameAndUrl nameAndUrl : abilityList.results()) {
            String[] split = nameAndUrl.url().split("/");
            AbilityResponse abilityResponse = pokeClient.getAbilityResponse(split[split.length - 1]);
            abilityResponses.add(abilityResponse);
        }

        return abilityResponses;
    }

    private List<PokemonAbility> getPokemonAbilities(List<AbilityResponse> abilityResponses) {
        List<PokemonAbility> pokemonAbilities = new ArrayList<>();
        for (AbilityResponse abilityResponse : abilityResponses) {
            PokemonAbility pokemonAbility = dtoParser.getPokemonAbility(abilityResponse);
            pokemonAbilities.add(pokemonAbility);
        }

        return pokemonAbilities;
    }

    public List<PokemonType> savePokemonTypeList() {
        ListResponse typeList = getTypeList();
        List<TypeResponse> typeResponses = getTypeResponses(typeList);
        List<PokemonType> pokemonTypes = getPokemonTypes(typeResponses);

        return pokemonTypeRepository.saveAll(pokemonTypes);
    }

    private ListResponse getTypeList() {
        CountResponse typeListSize = pokeClient.getTypeListSize();

        return pokeClient.getTypeList(String.valueOf(typeListSize.count()));
    }

    private List<TypeResponse> getTypeResponses(ListResponse typeList) {
        List<TypeResponse> typeResponses = new ArrayList<>();
        for (NameAndUrl nameAndUrl : typeList.results()) {
            String[] split = nameAndUrl.url().split("/");
            TypeResponse typeResponse = pokeClient.getTypeResponse(split[split.length - 1]);
            typeResponses.add(typeResponse);
        }

        return typeResponses;
    }

    private List<PokemonType> getPokemonTypes(List<TypeResponse> typeResponses) {
        List<PokemonType> pokemonTypes = new ArrayList<>();
        for (TypeResponse typeResponse : typeResponses) {
            PokemonType pokemonType = dtoParser.getPokemonType(typeResponse);
            pokemonTypes.add(pokemonType);
        }

        return pokemonTypes;
    }

    @Transactional
    public List<Pokemon> savePokemonList() {
        CountResponse pokemonListSize = pokeClient.getPokemonListSize();
        List<Pokemon> pokemons = new ArrayList<>();
        for (int i = 0; i < pokemonListSize.count(); i+=500) {
            ListResponse pokemonList = pokeClient.getPokemonList(String.valueOf(i), "500");
            for (NameAndUrl nameAndUrl : pokemonList.results()) {
                String[] split = nameAndUrl.url().split("/");
                PokemonSaveResponse pokemonSaveResponse = pokeClient.getPokemonSaveResponse(split[split.length - 1]);
                Pokemon pokemon = savePokemon(pokemonSaveResponse);
                pokemons.add(pokemon);
            }
        }

        return pokemons;
    }

    private Pokemon savePokemon(PokemonSaveResponse pokemonSaveResponse) {
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
        pokemonMapping(pokemonSaveResponse, savedPokemon);

        return savedPokemon;
    }

    private void pokemonMapping(PokemonSaveResponse pokemonSaveResponse, Pokemon savedPokemon) {
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
    }

    private PokemonSpeciesResponse getPokemonSpeciesResponse(NameAndUrl species) {
        String[] split = species.url().split("/");

        return pokeClient.getPokemonSpeciesResponse(split[split.length - 1]);
    }

    private PokemonNameAndDexNumber getPokemonNameAndDexNumber(PokemonSpeciesResponse pokemonSpeciesResponse) {
        return dtoParser.getPokemonNameAndDexNumber(pokemonSpeciesResponse);
    }
}
