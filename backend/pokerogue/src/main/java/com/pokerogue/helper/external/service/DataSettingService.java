package com.pokerogue.helper.external.service;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.external.infrastructure.DtoParser;
import com.pokerogue.helper.external.client.PokeClient;
import com.pokerogue.helper.external.dto.CountResponse;
import com.pokerogue.helper.external.dto.InformationLinks;
import com.pokerogue.helper.external.dto.InformationLink;
import com.pokerogue.helper.external.dto.ability.AbilityResponse;
import com.pokerogue.helper.external.dto.pokemon.AbilityInformationLink;
import com.pokerogue.helper.external.dto.pokemon.PokemonDetail;
import com.pokerogue.helper.external.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.helper.external.dto.pokemon.TypeInformationLink;
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

    private final PokemonRepository pokemonRepository;
    private final PokemonAbilityRepository pokemonAbilityRepository;
    private final PokemonTypeRepository pokemonTypeRepository;
    private final PokemonAbilityMappingRepository pokemonAbilityMappingRepository;
    private final PokemonTypeMappingRepository pokemonTypeMappingRepository;
    private final DtoParser dtoParser;
    private final PokeClient pokeClient;

    public void savePokemonAbilities() {
        pokemonAbilityMappingRepository.deleteAllInBatch();
        pokemonAbilityRepository.deleteAllInBatch();

        InformationLinks abilityInformationLinks = getAbilityInformationLinks();
        List<AbilityResponse> abilityResponses = getAbilityResponses(abilityInformationLinks);
        List<PokemonAbility> pokemonAbilities = getPokemonAbilities(abilityResponses);
        pokemonAbilityRepository.saveAll(pokemonAbilities);
    }

    private InformationLinks getAbilityInformationLinks() {
        CountResponse abilityCountResponse = pokeClient.getAbilityResponsesCount();

        return pokeClient.getAbilityResponses(abilityCountResponse.count());
    }

    private List<AbilityResponse> getAbilityResponses(InformationLinks abilityList) {
        return abilityList.results().stream()
                .map(nameAndUrl -> pokeClient.getAbilityResponse(extractIdFromUrl(nameAndUrl)))
                .toList();
    }

    private String extractIdFromUrl(InformationLink informationLink) {
        String[] tokens = informationLink.url().split("/");
        return tokens[tokens.length - 1];
    }

    private List<PokemonAbility> getPokemonAbilities(List<AbilityResponse> abilityResponses) {
        return abilityResponses.stream()
                .map(dtoParser::getPokemonAbility)
                .toList();
    }

    public void savePokemonTypes() {
        pokemonTypeMappingRepository.deleteAllInBatch();
        pokemonTypeRepository.deleteAllInBatch();

        InformationLinks typeInformationLinks = getTypeInformationLinks();
        List<TypeResponse> typeResponses = getTypeResponses(typeInformationLinks);
        List<PokemonType> pokemonTypes = getPokemonTypes(typeResponses);
        pokemonTypeRepository.saveAll(pokemonTypes);
    }

    private InformationLinks getTypeInformationLinks() {
        CountResponse typeCountResponse = pokeClient.getTypeResponsesCount();

        return pokeClient.getTypeResponses(typeCountResponse.count());
    }

    private List<TypeResponse> getTypeResponses(InformationLinks typeList) {
        return typeList.results().stream()
                .map(nameAndUrl -> pokeClient.getTypeResponse(extractIdFromUrl(nameAndUrl)))
                .toList();
    }

    private List<PokemonType> getPokemonTypes(List<TypeResponse> typeResponses) {
        return typeResponses.stream()
                .map(dtoParser::getPokemonType)
                .toList();
    }

    @Transactional
    public void savePokemons() {
        pokemonAbilityMappingRepository.deleteAllInBatch();
        pokemonTypeMappingRepository.deleteAllInBatch();
        pokemonRepository.deleteAllInBatch();

        CountResponse pokemonCountResponse = pokeClient.getPokemonResponsesCount();
        for (int offset = 0; offset < pokemonCountResponse.count(); offset += 500) {
            InformationLinks pokemonInformationLinks = pokeClient.getPokemonResponses(offset, 500);
            for (InformationLink informationLink : pokemonInformationLinks.results()) {
                PokemonSaveResponse pokemonSaveResponse = pokeClient.getPokemonSaveResponse(extractIdFromUrl(
                        informationLink));
                savePokemon(pokemonSaveResponse);
            }
        }
    }

    private void savePokemon(PokemonSaveResponse pokemonSaveResponse) {
        PokemonDetail pokemonDetail = dtoParser.getPokemonDetails(pokemonSaveResponse);
        InformationLink species = pokemonDetail.species();
        PokemonNameAndDexNumber pokemonNameAndDexNumber = getPokemonNameAndDexNumber(getPokemonSpeciesResponse(species));

        Pokemon pokemon = new Pokemon(pokemonNameAndDexNumber.pokedexNumber(), pokemonDetail.name(),
                pokemonNameAndDexNumber.koName(), pokemonDetail.weight(), pokemonDetail.height(), pokemonDetail.hp(),
                pokemonDetail.speed(), pokemonDetail.attack(), pokemonDetail.defense(), pokemonDetail.specialAttack(),
                pokemonDetail.specialDefense(), pokemonDetail.totalStats(), "null");
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        savePokemonMapping(pokemonSaveResponse, savedPokemon);
    }

    private void savePokemonMapping(PokemonSaveResponse pokemonSaveResponse, Pokemon savedPokemon) {
        List<AbilityInformationLink> abilities = pokemonSaveResponse.abilities();
        for (AbilityInformationLink abilityInformationLink : abilities) {
            String name = abilityInformationLink.ability().name();
            PokemonAbility pokemonAbility = pokemonAbilityRepository.findByName(name)
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
            PokemonAbilityMapping pokemonAbilityMapping = new PokemonAbilityMapping(savedPokemon, pokemonAbility);
            pokemonAbilityMappingRepository.save(pokemonAbilityMapping);
        }

        List<TypeInformationLink> types = pokemonSaveResponse.types();
        for (TypeInformationLink typeInformationLink : types) {
            String name = typeInformationLink.type().name();
            PokemonType pokemonType = pokemonTypeRepository.findByName(name)
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
            PokemonTypeMapping pokemonTypeMapping = new PokemonTypeMapping(savedPokemon, pokemonType);
            pokemonTypeMappingRepository.save(pokemonTypeMapping);
        }
    }

    private PokemonSpeciesResponse getPokemonSpeciesResponse(InformationLink species) {
        return pokeClient.getPokemonSpeciesResponse(extractIdFromUrl(species));
    }

    private PokemonNameAndDexNumber getPokemonNameAndDexNumber(PokemonSpeciesResponse pokemonSpeciesResponse) {
        return dtoParser.getPokemonNameAndDexNumber(pokemonSpeciesResponse);
    }
}
