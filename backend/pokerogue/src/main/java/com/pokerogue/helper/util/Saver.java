package com.pokerogue.helper.util;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.external.client.PokeClient;
import com.pokerogue.helper.external.dto.CountResponse;
import com.pokerogue.helper.external.dto.ListResponse;
import com.pokerogue.helper.external.dto.NameAndUrl;
import com.pokerogue.helper.external.dto.ability.AbilityResponse;
import com.pokerogue.helper.external.dto.pokemon.AbilitySummary;
import com.pokerogue.helper.external.dto.pokemon.PokemonDetail;
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
        pokemonAbilityRepository.deleteAllInBatch();

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
        return abilityList.results().stream()
                .map(nameAndUrl -> nameAndUrl.url().split("/"))
                .map(tokens -> pokeClient.getAbilityResponse(tokens[tokens.length - 1]))
                .toList();
    }

    private List<PokemonAbility> getPokemonAbilities(List<AbilityResponse> abilityResponses) {
        return abilityResponses.stream()
                .map(abilityResponse -> dtoParser.getPokemonAbility(abilityResponse))
                .toList();
    }

    public List<PokemonType> savePokemonTypeList() {
        pokemonTypeRepository.deleteAllInBatch();

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
        return typeList.results().stream()
                .map(nameAndUrl -> nameAndUrl.url().split("/"))
                .map(tokens -> pokeClient.getTypeResponse(tokens[tokens.length - 1]))
                .toList();
    }

    private List<PokemonType> getPokemonTypes(List<TypeResponse> typeResponses) {
        return typeResponses.stream()
                .map(typeResponse -> dtoParser.getPokemonType(typeResponse))
                .toList();
    }

    @Transactional
    public List<Pokemon> savePokemonList() {
        pokemonAbilityMappingRepository.deleteAllInBatch();
        pokemonTypeMappingRepository.deleteAllInBatch();
        pokemonRepository.deleteAllInBatch();

        CountResponse pokemonListSize = pokeClient.getPokemonListSize();
        List<Pokemon> pokemons = new ArrayList<>();
        for (int i = 0; i < pokemonListSize.count(); i += 500) {
            ListResponse pokemonList = pokeClient.getPokemonList(String.valueOf(i), "500");
            for (NameAndUrl nameAndUrl : pokemonList.results()) {
                String[] tokens = nameAndUrl.url().split("/");
                PokemonSaveResponse pokemonSaveResponse = pokeClient.getPokemonSaveResponse(tokens[tokens.length - 1]);
                Pokemon pokemon = savePokemon(pokemonSaveResponse);
                pokemons.add(pokemon);
            }
        }

        return pokemons;
    }

    private Pokemon savePokemon(PokemonSaveResponse pokemonSaveResponse) {
        PokemonDetail pokemonDetail = dtoParser.getPokemonDetails(pokemonSaveResponse);
        NameAndUrl species = pokemonDetail.species();
        PokemonNameAndDexNumber pokemonNameAndDexNumber = getPokemonNameAndDexNumber(getPokemonSpeciesResponse(species));

        Pokemon pokemon = new Pokemon(null, pokemonNameAndDexNumber.pokedexNumber(), pokemonDetail.name(),
                pokemonNameAndDexNumber.koName(), pokemonDetail.weight(), pokemonDetail.height(), pokemonDetail.hp(),
                pokemonDetail.speed(), pokemonDetail.attack(), pokemonDetail.defense(), pokemonDetail.specialAttack(),
                pokemonDetail.specialDefense(), pokemonDetail.totalStats(), "null",
                new ArrayList<>(), new ArrayList<>());
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
        String[] tokens = species.url().split("/");

        return pokeClient.getPokemonSpeciesResponse(tokens[tokens.length - 1]);
    }

    private PokemonNameAndDexNumber getPokemonNameAndDexNumber(PokemonSpeciesResponse pokemonSpeciesResponse) {
        return dtoParser.getPokemonNameAndDexNumber(pokemonSpeciesResponse);
    }
}
