package com.pokerogue.external.pokemon.service;

import com.pokerogue.external.pokemon.client.PokeClient;
import com.pokerogue.external.pokemon.dto.CountResponse;
import com.pokerogue.external.pokemon.dto.DataUrl;
import com.pokerogue.external.pokemon.dto.DataUrls;
import com.pokerogue.external.pokemon.dto.ability.AbilityResponse;
import com.pokerogue.external.pokemon.dto.pokemon.AbilityDataUrl;
import com.pokerogue.external.pokemon.dto.pokemon.PokemonDetail;
import com.pokerogue.external.pokemon.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.external.pokemon.dto.pokemon.TypeDataUrl;
import com.pokerogue.external.pokemon.dto.pokemon.species.PokemonNameAndDexNumber;
import com.pokerogue.external.pokemon.dto.pokemon.species.PokemonSpeciesResponse;
import com.pokerogue.external.pokemon.dto.type.TypeMatchingResponse;
import com.pokerogue.external.pokemon.dto.type.TypeResponse;
import com.pokerogue.external.pokemon.parser.DtoParser;
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
    private static final int NO_USE_TYPE_COUNT = 2;

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
        savePokemons();
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
        CountResponse typeCountResponse = pokeClient.getTypeResponsesCount();

        return pokeClient.getTypeResponses(typeCountResponse.count() - NO_USE_TYPE_COUNT);
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
            String fromTypeKoName = pokemonTypeRepository.findByName(dataUrl.name()).orElseThrow().getKoName();
            TypeMatchingResponse typeMatchingResponse = pokeClient.getTypeMatchingResponse(extractIdFromUrl(dataUrl));

            savePokemonTypeMatching(typeMatchingResponse, fromTypeKoName);
        }
    }

    private void savePokemonTypeMatching(TypeMatchingResponse typeMatchingResponse, String fromTypeKoName) {
        List<String> allTypeNames = getAllTypeNames();

        saveDoubleDamageTypeMatching(typeMatchingResponse, fromTypeKoName, allTypeNames);
        saveHalfDamageTypeMatching(typeMatchingResponse, fromTypeKoName, allTypeNames);
        saveNoDamageTypeMatching(typeMatchingResponse, fromTypeKoName, allTypeNames);
        saveBasicDamageTypeMatching(fromTypeKoName, allTypeNames);
    }

    private List<String> getAllTypeNames() {
        return pokemonTypeRepository.findAll().stream()
                .map(PokemonType::getKoName)
                .collect(Collectors.toList());
    }

    private void saveDoubleDamageTypeMatching(TypeMatchingResponse typeMatchingResponse, String fromTypeKoName, List<String> allTypeNames) {
        for (DataUrl type : typeMatchingResponse.getDoubleDamageTo()) {
            PokemonType pokemonType = pokemonTypeRepository.findByName(type.name())
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
            String toTypeKoName = pokemonType.getKoName();
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromTypeKoName, toTypeKoName, DOUBLE_DAMAGE);

            pokemonTypeMatchingRepository.save(pokemonTypeMatching);
            allTypeNames.remove(toTypeKoName);
        }
    }

    private void saveHalfDamageTypeMatching(TypeMatchingResponse typeMatchingResponse, String fromTypeKoName, List<String> allTypeNames) {
        for (DataUrl type : typeMatchingResponse.getHalfDamageTo()) {
            PokemonType pokemonType = pokemonTypeRepository.findByName(type.name())
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
            String toTypeKoName = pokemonType.getKoName();
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromTypeKoName, toTypeKoName, HALF_DAMAGE);

            pokemonTypeMatchingRepository.save(pokemonTypeMatching);
            allTypeNames.remove(toTypeKoName);
        }
    }

    private void saveNoDamageTypeMatching(TypeMatchingResponse typeMatchingResponse, String fromTypeKoName, List<String> allTypeNames) {
        for (DataUrl type : typeMatchingResponse.getNoDamageTo()) {
            PokemonType pokemonType = pokemonTypeRepository.findByName(type.name())
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
            String toTypeKoName = pokemonType.getKoName();
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromTypeKoName, toTypeKoName, NO_DAMAGE);

            pokemonTypeMatchingRepository.save(pokemonTypeMatching);
            allTypeNames.remove(toTypeKoName);
        }
    }

    private void saveBasicDamageTypeMatching(String fromTypeKoName, List<String> allTypeNames) {
        for (String toTypeKoName : allTypeNames) {
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromTypeKoName, toTypeKoName, BASIC_DAMAGE);

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

    private void savePokemons() {
        CountResponse pokemonCountResponse = pokeClient.getPokemonResponsesCount();
        for (int offset = 0; offset < pokemonCountResponse.count(); offset += PACKET_SIZE) {
            savePokemonsByOffset(offset);
        }
    }

    private void savePokemonsByOffset(int offset) {
        DataUrls pokemonDataUrls = pokeClient.getPokemonResponses(offset, PACKET_SIZE);
        for (DataUrl dataUrl : pokemonDataUrls.results()) {
            PokemonSaveResponse pokemonSaveResponse = pokeClient.getPokemonSaveResponse(extractIdFromUrl(dataUrl));

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
            String name = typeDataUrl.getName();
            PokemonType pokemonType = pokemonTypeRepository.findByName(name)
                    .orElseThrow(() -> new GlobalCustomException(ErrorMessage.POKEMON_TYPE_NOT_FOUND));
            PokemonTypeMapping pokemonTypeMapping = new PokemonTypeMapping(savedPokemon, pokemonType);

            pokemonTypeMappingRepository.save(pokemonTypeMapping);
        }
    }

    private void savePokemonAbilityMapping(PokemonSaveResponse pokemonSaveResponse, Pokemon savedPokemon) {
        List<AbilityDataUrl> abilities = pokemonSaveResponse.abilities();
        for (AbilityDataUrl abilityDataUrl : abilities) {
            String name = abilityDataUrl.getName();
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
