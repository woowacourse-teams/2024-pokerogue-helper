package com.pokerogue.external.service;

import com.pokerogue.external.client.PokeClient;
import com.pokerogue.external.dto.CountResponse;
import com.pokerogue.external.dto.DataUrl;
import com.pokerogue.external.dto.DataUrls;
import com.pokerogue.external.dto.ability.AbilityResponse;
import com.pokerogue.external.dto.pokemon.AbilityDataUrl;
import com.pokerogue.external.dto.pokemon.PokemonDetail;
import com.pokerogue.external.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.external.dto.pokemon.TypeDataUrl;
import com.pokerogue.external.dto.pokemon.species.PokemonNameAndDexNumber;
import com.pokerogue.external.dto.pokemon.species.PokemonSpeciesResponse;
import com.pokerogue.external.dto.type.TypeMatchingResponse;
import com.pokerogue.external.dto.type.TypeResponse;
import com.pokerogue.external.infrastructure.DtoParser;
import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import com.pokerogue.helper.pokemon.repository.PokemonAbilityMappingRepository;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.pokemon.repository.PokemonTypeMappingRepository;
import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.type.domain.PokemonTypeMatching;
import com.pokerogue.helper.type.repository.PokemonTypeMatchingRepository;
import com.pokerogue.helper.type.repository.PokemonTypeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DataSettingService {

    private static final int PACKET_SIZE = 500;
    private static final double DOUBLE_DAMAGE = 2.0;
    private static final double HALF_DAMAGE = 0.5;
    private static final double NO_DAMAGE = 0.0;
    private static final double BASIC_DAMAGE = 1.0;

    private final PokemonRepository pokemonRepository;
    private final PokemonAbilityRepository pokemonAbilityRepository;
    private final PokemonTypeRepository pokemonTypeRepository;
    private final PokemonAbilityMappingRepository pokemonAbilityMappingRepository;
    private final PokemonTypeMappingRepository pokemonTypeMappingRepository;
    private final PokemonTypeMatchingRepository pokemonTypeMatchingRepository;
    private final DtoParser dtoParser;
    private final PokeClient pokeClient;

    @Transactional
    public void setData() {
        deleteAll();
        savePokemonTypes();
        savePokemonAbilities();
        saveAllPokemons();
    }

    private void deleteAll() {
        pokemonTypeMappingRepository.deleteAllInBatch();
        pokemonAbilityMappingRepository.deleteAllInBatch();
        pokemonTypeMatchingRepository.deleteAllInBatch();
        pokemonTypeRepository.deleteAllInBatch();
        pokemonAbilityRepository.deleteAllInBatch();
        pokemonRepository.deleteAllInBatch();
    }

    private void savePokemonTypes() {
        DataUrls typeDataUrls = getTypeDataUrls();
        List<TypeResponse> typeResponses = getTypeResponses(typeDataUrls);
        List<PokemonType> pokemonTypes = getPokemonTypes(typeResponses);
        pokemonTypeRepository.saveAll(pokemonTypes);
        saveAllPokemonTypeMatching(typeDataUrls);
    }

    private DataUrls getTypeDataUrls() {
        return pokeClient.getTypeResponses(18);
    }

    private List<TypeResponse> getTypeResponses(DataUrls dataUrls) {
        return dataUrls.results().stream()
                .map(dataUrl -> pokeClient.getTypeResponse(extractIdFromUrl(dataUrl)))
                .toList();
    }

    private List<PokemonType> getPokemonTypes(List<TypeResponse> typeResponses) {
        return typeResponses.stream()
                .map(dtoParser::getPokemonType)
                .toList();
    }

    private void saveAllPokemonTypeMatching(DataUrls typeDataUrls) {
        for (DataUrl dataUrl : typeDataUrls.results()) {
            String fromKoName = pokemonTypeRepository.findByName(dataUrl.name()).orElseThrow().getKoName();
            TypeMatchingResponse typeMatchingResponse = pokeClient.getTypeMatchingResponse(extractIdFromUrl(dataUrl));

            savePokemonTypeMatching(typeMatchingResponse, fromKoName);
        }
    }

    private void savePokemonTypeMatching(TypeMatchingResponse typeMatchingResponse, String fromKoName) {
        List<String> allTypeNames = getAllTypeNames();
        saveDoubleDamageTypeMatching(typeMatchingResponse, fromKoName, allTypeNames);
        saveHalfDamageTypeMatching(typeMatchingResponse, fromKoName, allTypeNames);
        saveNoDamageTypeMatching(typeMatchingResponse, fromKoName, allTypeNames);
        saveBasicDamageTypeMatching(fromKoName, allTypeNames);
    }

    private List<String> getAllTypeNames() {
        List<String> typeNames = pokemonTypeRepository.findAll().stream()
                .map(PokemonType::getKoName)
                .collect(Collectors.toList());
        typeNames.remove("스텔라");
        typeNames.remove("???");
        typeNames.remove("다크");

        return typeNames;
    }

    private void saveDoubleDamageTypeMatching(TypeMatchingResponse typeMatchingResponse, String fromKoName, List<String> allTypeNames) {
        for (DataUrl type : typeMatchingResponse.getDoubleDamageTo()) {
            String toKoName = pokemonTypeRepository.findByName(type.name()).orElseThrow().getKoName();
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromKoName, toKoName, DOUBLE_DAMAGE);
            pokemonTypeMatchingRepository.save(pokemonTypeMatching);
            allTypeNames.remove(toKoName);
        }
    }

    private void saveHalfDamageTypeMatching(TypeMatchingResponse typeMatchingResponse, String fromKoName, List<String> allTypeNames) {
        for (DataUrl type : typeMatchingResponse.getHalfDamageTo()) {
            String toKoName = pokemonTypeRepository.findByName(type.name()).orElseThrow().getKoName();
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromKoName, toKoName, HALF_DAMAGE);
            pokemonTypeMatchingRepository.save(pokemonTypeMatching);
            allTypeNames.remove(toKoName);
        }
    }

    private void saveNoDamageTypeMatching(TypeMatchingResponse typeMatchingResponse, String fromKoName, List<String> allTypeNames) {
        for (DataUrl type : typeMatchingResponse.getNoDamageTo()) {
            String toKoName = pokemonTypeRepository.findByName(type.name()).orElseThrow().getKoName();
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromKoName, toKoName, NO_DAMAGE);
            pokemonTypeMatchingRepository.save(pokemonTypeMatching);
            allTypeNames.remove(toKoName);
        }
    }

    private void saveBasicDamageTypeMatching(String fromKoName, List<String> allTypeNames) {
        for (String toKoName : allTypeNames) {
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromKoName, toKoName, BASIC_DAMAGE);
            pokemonTypeMatchingRepository.save(pokemonTypeMatching);
        }
    }

    private void savePokemonAbilities() {
        DataUrls abilityDataUrls = getAbilityDataUrls();
        List<AbilityResponse> abilityResponses = getAbilityResponses(abilityDataUrls);
        List<PokemonAbility> pokemonAbilities = getPokemonAbilities(abilityResponses);
        pokemonAbilityRepository.saveAll(pokemonAbilities);
    }

    private DataUrls getAbilityDataUrls() {
        CountResponse abilityCountResponse = pokeClient.getAbilityResponsesCount();

        return pokeClient.getAbilityResponses(abilityCountResponse.count());
    }

    private List<AbilityResponse> getAbilityResponses(DataUrls abilityList) {
        return abilityList.results().stream()
                .map(dataUrl -> pokeClient.getAbilityResponse(extractIdFromUrl(dataUrl)))
                .toList();
    }

    private List<PokemonAbility> getPokemonAbilities(List<AbilityResponse> abilityResponses) {
        return abilityResponses.stream()
                .map(dtoParser::getPokemonAbility)
                .toList();
    }

    private void saveAllPokemons() {
        CountResponse pokemonCountResponse = pokeClient.getPokemonResponsesCount();
        for (int offset = 0; offset < pokemonCountResponse.count(); offset += PACKET_SIZE) {
            savePokemons(offset);
        }
    }

    private void savePokemons(int offset) {
        DataUrls pokemonDataUrls = pokeClient.getPokemonResponses(offset, PACKET_SIZE);
        for (DataUrl dataUrl : pokemonDataUrls.results()) {
            PokemonSaveResponse pokemonSaveResponse = pokeClient.getPokemonSaveResponse(extractIdFromUrl(
                    dataUrl));
            savePokemon(pokemonSaveResponse);
        }
    }

    private void savePokemon(PokemonSaveResponse pokemonSaveResponse) {
        PokemonDetail pokemonDetail = dtoParser.getPokemonDetails(pokemonSaveResponse);
        DataUrl species = pokemonDetail.species();
        PokemonNameAndDexNumber pokemonNameAndDexNumber = getPokemonNameAndDexNumber(getPokemonSpeciesResponse(species));

        Pokemon pokemon = new Pokemon(pokemonNameAndDexNumber.pokedexNumber(), pokemonDetail.name(),
                pokemonNameAndDexNumber.koName(), pokemonDetail.weight(), pokemonDetail.height(), pokemonDetail.hp(),
                pokemonDetail.speed(), pokemonDetail.attack(), pokemonDetail.defense(), pokemonDetail.specialAttack(),
                pokemonDetail.specialDefense(), pokemonDetail.totalStats(), "null");
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        savePokemonTypeMapping(pokemonSaveResponse, savedPokemon);
        savePokemonAbilityMapping(pokemonSaveResponse, savedPokemon);
    }

    private PokemonSpeciesResponse getPokemonSpeciesResponse(DataUrl species) {
        return pokeClient.getPokemonSpeciesResponse(extractIdFromUrl(species));
    }

    private PokemonNameAndDexNumber getPokemonNameAndDexNumber(PokemonSpeciesResponse pokemonSpeciesResponse) {
        return dtoParser.getPokemonNameAndDexNumber(pokemonSpeciesResponse);
    }

    private void savePokemonTypeMapping(PokemonSaveResponse pokemonSaveResponse, Pokemon savedPokemon) {
        List<TypeDataUrl> types = pokemonSaveResponse.types();
        for (TypeDataUrl typeDataUrl : types) {
            String name = typeDataUrl.type().name();
            PokemonType pokemonType = pokemonTypeRepository.findByName(name)
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
            PokemonTypeMapping pokemonTypeMapping = new PokemonTypeMapping(savedPokemon, pokemonType);
            pokemonTypeMappingRepository.save(pokemonTypeMapping);
        }
    }

    private void savePokemonAbilityMapping(PokemonSaveResponse pokemonSaveResponse, Pokemon savedPokemon) {
        List<AbilityDataUrl> abilities = pokemonSaveResponse.abilities();
        for (AbilityDataUrl abilityDataUrl : abilities) {
            String name = abilityDataUrl.ability().name();
            PokemonAbility pokemonAbility = pokemonAbilityRepository.findByName(name)
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_ABILITY_NOT_FOUND));
            PokemonAbilityMapping pokemonAbilityMapping = new PokemonAbilityMapping(savedPokemon, pokemonAbility);
            pokemonAbilityMappingRepository.save(pokemonAbilityMapping);
        }
    }

    private String extractIdFromUrl(DataUrl dataUrl) {
        String[] tokens = dataUrl.url().split("/");
        return tokens[tokens.length - 1];
    }
}
