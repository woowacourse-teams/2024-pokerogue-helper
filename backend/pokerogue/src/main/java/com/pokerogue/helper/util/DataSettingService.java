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
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DataSettingService {
    private PokemonRepository pokemonRepository;
    private PokemonAbilityRepository pokemonAbilityRepository;
    private PokemonTypeRepository pokemonTypeRepository;
    private PokemonAbilityMappingRepository pokemonAbilityMappingRepository;
    private PokemonTypeMappingRepository pokemonTypeMappingRepository;
    private DtoParser dtoParser;
    private PokeClient pokeClient;

    public void savePokemonAbilities() {
        pokemonAbilityMappingRepository.deleteAllInBatch();
        pokemonTypeMappingRepository.deleteAllInBatch();
        pokemonAbilityRepository.deleteAllInBatch();

        ListResponse abilityList = getAbilityList();
        List<AbilityResponse> abilityResponses = getAbilityResponses(abilityList);
        List<PokemonAbility> pokemonAbilities = getPokemonAbilities(abilityResponses);
        pokemonAbilityRepository.saveAll(pokemonAbilities);
    }

    private ListResponse getAbilityList() {
        CountResponse abilityCountResponse = pokeClient.getAbilityResponsesCount();

        return pokeClient.getAbilityResponses(abilityCountResponse.count());
    }

    private List<AbilityResponse> getAbilityResponses(ListResponse abilityList) {
        return abilityList.results().stream()
                .map(nameAndUrl -> pokeClient.getAbilityResponse(extractIdFromUrl(nameAndUrl)))
                .toList();
    }

    private String extractIdFromUrl(NameAndUrl nameAndUrl) {
        String[] tokens = nameAndUrl.url().split("/");
        return tokens[tokens.length - 1];
    }

    private List<PokemonAbility> getPokemonAbilities(List<AbilityResponse> abilityResponses) {
        return abilityResponses.stream()
                .map(abilityResponse -> dtoParser.getPokemonAbility(abilityResponse))
                .toList();
    }

    public void savePokemonTypes() {
        pokemonAbilityMappingRepository.deleteAllInBatch();
        pokemonTypeMappingRepository.deleteAllInBatch();
        pokemonTypeRepository.deleteAllInBatch();

        ListResponse typeList = getTypeList();
        List<TypeResponse> typeResponses = getTypeResponses(typeList);
        List<PokemonType> pokemonTypes = getPokemonTypes(typeResponses);
        pokemonTypeRepository.saveAll(pokemonTypes);
    }

    private ListResponse getTypeList() {
        CountResponse typeCountResponse = pokeClient.getTypeResponsesCount();

        return pokeClient.getTypeResponses(typeCountResponse.count());
    }

    private List<TypeResponse> getTypeResponses(ListResponse typeList) {
        return typeList.results().stream()
                .map(nameAndUrl -> pokeClient.getTypeResponse(extractIdFromUrl(nameAndUrl)))
                .toList();
    }

    private List<PokemonType> getPokemonTypes(List<TypeResponse> typeResponses) {
        return typeResponses.stream()
                .map(typeResponse -> dtoParser.getPokemonType(typeResponse))
                .toList();
    }

    @Transactional
    public void savePokemons() {
        pokemonAbilityMappingRepository.deleteAllInBatch();
        pokemonTypeMappingRepository.deleteAllInBatch();
        pokemonRepository.deleteAllInBatch();

        CountResponse pokemonCountResponse = pokeClient.getPokemonResponsesCount();
        for (int offset = 0; offset < pokemonCountResponse.count(); offset += 500) {
            ListResponse pokemonList = pokeClient.getPokemonResponses(offset, 500);
            for (NameAndUrl nameAndUrl : pokemonList.results()) {
                PokemonSaveResponse pokemonSaveResponse = pokeClient.getPokemonSaveResponse(extractIdFromUrl(nameAndUrl));
                savePokemon(pokemonSaveResponse);
            }
        }
    }

    private void savePokemon(PokemonSaveResponse pokemonSaveResponse) {
        PokemonDetail pokemonDetail = dtoParser.getPokemonDetails(pokemonSaveResponse);
        NameAndUrl species = pokemonDetail.species();
        PokemonNameAndDexNumber pokemonNameAndDexNumber = getPokemonNameAndDexNumber(getPokemonSpeciesResponse(species));

        Pokemon pokemon = new Pokemon(pokemonNameAndDexNumber.pokedexNumber(), pokemonDetail.name(),
                pokemonNameAndDexNumber.koName(), pokemonDetail.weight(), pokemonDetail.height(), pokemonDetail.hp(),
                pokemonDetail.speed(), pokemonDetail.attack(), pokemonDetail.defense(), pokemonDetail.specialAttack(),
                pokemonDetail.specialDefense(), pokemonDetail.totalStats(), "null");
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        savePokemonMapping(pokemonSaveResponse, savedPokemon);
    }

    private void savePokemonMapping(PokemonSaveResponse pokemonSaveResponse, Pokemon savedPokemon) {
        List<AbilitySummary> abilities = pokemonSaveResponse.abilities();
        for (AbilitySummary abilitySummary : abilities) {
            String name = abilitySummary.ability().name();
            PokemonAbility pokemonAbility = pokemonAbilityRepository.findByName(name)
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
            PokemonAbilityMapping pokemonAbilityMapping = new PokemonAbilityMapping(savedPokemon, pokemonAbility);
            savedPokemon.getPokemonAbilityMappings().add(pokemonAbilityMapping);
            pokemonAbility.getPokemonAbilityMappings().add(pokemonAbilityMapping);
            pokemonAbilityMappingRepository.save(pokemonAbilityMapping);
        }

        List<TypeSummary> types = pokemonSaveResponse.types();
        for (TypeSummary typeSummary : types) {
            String name = typeSummary.type().name();
            PokemonType pokemonType = pokemonTypeRepository.findByName(name)
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
            PokemonTypeMapping pokemonTypeMapping = new PokemonTypeMapping(savedPokemon, pokemonType);
            savedPokemon.getPokemonTypeMappings().add(pokemonTypeMapping);
            pokemonTypeMappingRepository.save(pokemonTypeMapping);
        }
    }

    private PokemonSpeciesResponse getPokemonSpeciesResponse(NameAndUrl species) {
        return pokeClient.getPokemonSpeciesResponse(extractIdFromUrl(species));
    }

    private PokemonNameAndDexNumber getPokemonNameAndDexNumber(PokemonSpeciesResponse pokemonSpeciesResponse) {
        return dtoParser.getPokemonNameAndDexNumber(pokemonSpeciesResponse);
    }
}
