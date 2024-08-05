package com.pokerogue.external.pokemon.service;

import com.pokerogue.external.pokemon.client.PokeClient;
import com.pokerogue.external.pokemon.dto.CountResponse;
import com.pokerogue.external.pokemon.dto.DataUrl;
import com.pokerogue.external.pokemon.dto.DataUrls;
import com.pokerogue.external.pokemon.dto.ability.AbilityResponse;
import com.pokerogue.external.pokemon.dto.pokemon.PokemonDetail;
import com.pokerogue.external.pokemon.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.external.pokemon.dto.pokemon.species.PokemonNameAndDexNumber;
import com.pokerogue.external.pokemon.dto.pokemon.species.PokemonSpeciesResponse;
import com.pokerogue.external.pokemon.dto.type.TypeMatchingResponse;
import com.pokerogue.external.pokemon.dto.type.TypeResponse;
import com.pokerogue.external.pokemon.parser.DtoParser;
import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

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
    private final S3Service s3Service;

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

        Map<String, PokemonType> pokemonTypeCacheByName = pokemonTypeRepository.saveAll(pokemonTypes).stream()
                .collect(Collectors.toMap(PokemonType::getName, Function.identity()));

        saveAllPokemonTypeMatching(typeDataUrls, pokemonTypeCacheByName);
    }

    private DataUrls getTypeDataUrls() {
        CountResponse typeCountResponse = pokeClient.getTypeResponsesCount();

        return pokeClient.getTypeResponses(typeCountResponse.count() - NO_USE_TYPE_COUNT);
    }

    private List<TypeResponse> getTypeResponses(DataUrls dataUrls) {
        return dataUrls.results().stream()
                .map(dataUrl -> pokeClient.getTypeResponse(dataUrl.getUrlId()))
                .toList();
    }

    private List<PokemonType> getPokemonTypes(List<TypeResponse> typeResponses) {
        return typeResponses.stream()
                .map(typeResponse -> dtoParser.getPokemonType(typeResponse,
                        s3Service.getTypeImageFromS3(typeResponse.name())))
                .toList();
    }

    private void saveAllPokemonTypeMatching(DataUrls typeDataUrls, Map<String, PokemonType> pokemonTypeCacheByName) {
        List<PokemonTypeMatching> pokemonTypeMatchings = new ArrayList<>();
        for (DataUrl dataUrl : typeDataUrls.results()) {
            PokemonType fromPokemonType = pokemonTypeCacheByName.get(dataUrl.name());
            TypeMatchingResponse typeMatchingResponse = pokeClient.getTypeMatchingResponse(dataUrl.getUrlId());

            savePokemonTypeMatching(typeMatchingResponse, fromPokemonType, pokemonTypeMatchings, pokemonTypeCacheByName);
        }
        pokemonTypeMatchingRepository.saveAll(pokemonTypeMatchings);
    }

    private void savePokemonTypeMatching(
            TypeMatchingResponse typeMatchingResponse,
            PokemonType fromPokemonType,
            List<PokemonTypeMatching> pokemonTypeMatchings,
            Map<String, PokemonType> pokemonTypeCacheByName
    ) {
        List<PokemonType> allPokemonTypes = new ArrayList<>(pokemonTypeCacheByName.values());

        saveDoubleDamageTypeMatching(
                typeMatchingResponse,
                fromPokemonType,
                allPokemonTypes,
                pokemonTypeMatchings,
                pokemonTypeCacheByName);
        saveHalfDamageTypeMatching(
                typeMatchingResponse,
                fromPokemonType,
                allPokemonTypes,
                pokemonTypeMatchings,
                pokemonTypeCacheByName);
        saveNoDamageTypeMatching(
                typeMatchingResponse,
                fromPokemonType,
                allPokemonTypes,
                pokemonTypeMatchings,
                pokemonTypeCacheByName);
        saveBasicDamageTypeMatching(fromPokemonType, allPokemonTypes, pokemonTypeMatchings);
    }

    private void saveDoubleDamageTypeMatching(
            TypeMatchingResponse typeMatchingResponse,
            PokemonType fromPokemonType,
            List<PokemonType> allPokemonTypes,
            List<PokemonTypeMatching> pokemonTypeMatchings,
            Map<String, PokemonType> pokemonTypeCacheByName
    ) {
        for (DataUrl type : typeMatchingResponse.getDoubleDamageTo()) {
            PokemonType toPokemonType = pokemonTypeCacheByName.get(type.name());
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromPokemonType, toPokemonType,
                    DOUBLE_DAMAGE);

            pokemonTypeMatchings.add(pokemonTypeMatching);
            allPokemonTypes.remove(toPokemonType);
        }
    }

    private void saveHalfDamageTypeMatching(
            TypeMatchingResponse typeMatchingResponse,
            PokemonType fromPokemonType,
            List<PokemonType> allPokemonTypes,
            List<PokemonTypeMatching> pokemonTypeMatchings,
            Map<String, PokemonType> pokemonTypeCacheByName
    ) {
        for (DataUrl type : typeMatchingResponse.getHalfDamageTo()) {
            PokemonType toPokemonType = pokemonTypeCacheByName.get(type.name());
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromPokemonType, toPokemonType,
                    HALF_DAMAGE);

            pokemonTypeMatchings.add(pokemonTypeMatching);
            allPokemonTypes.remove(toPokemonType);
        }
    }

    private void saveNoDamageTypeMatching(
            TypeMatchingResponse typeMatchingResponse,
            PokemonType fromPokemonType,
            List<PokemonType> allPokemonTypes,
            List<PokemonTypeMatching> pokemonTypeMatchings,
            Map<String, PokemonType> pokemonTypeCacheByName
    ) {
        for (DataUrl type : typeMatchingResponse.getNoDamageTo()) {
            PokemonType toPokemonType = pokemonTypeCacheByName.get(type.name());
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromPokemonType, toPokemonType,
                    NO_DAMAGE);

            pokemonTypeMatchings.add(pokemonTypeMatching);
            allPokemonTypes.remove(toPokemonType);
        }
    }

    private void saveBasicDamageTypeMatching(
            PokemonType fromPokemonType,
            List<PokemonType> allPokemonTypes,
            List<PokemonTypeMatching> pokemonTypeMatchings
    ) {
        for (PokemonType toPokemonType : allPokemonTypes) {
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromPokemonType, toPokemonType,
                    BASIC_DAMAGE);

            pokemonTypeMatchings.add(pokemonTypeMatching);
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
                .map(dataUrl -> pokeClient.getAbilityResponse(dataUrl.getUrlId()))
                .toList();
    }

    private List<PokemonAbility> getPokemonAbilities(List<AbilityResponse> abilityResponses) {
        return abilityResponses.stream()
                .map(dtoParser::getPokemonAbility)
                .toList();
    }

    private void savePokemons() {
        CountResponse pokemonCountResponse = pokeClient.getPokemonResponsesCount();
        List<Pokemon> pokemons = new ArrayList<>();
        List<PokemonSaveResponse> pokemonSaveResponses = new ArrayList<>();
        for (int offset = 0; offset < pokemonCountResponse.count(); offset += PACKET_SIZE) {
            savePokemonsByOffset(offset, pokemons, pokemonSaveResponses);
        }
        Map<String, Pokemon> pokemonCacheByName = pokemonRepository.saveAll(pokemons).stream()
                .collect(Collectors.toMap(Pokemon::getName, Function.identity()));
        Map<String, PokemonType> pokemonTypeCacheByName = pokemonTypeRepository.findAll().stream()
                .collect(Collectors.toMap(PokemonType::getName, Function.identity()));
        Map<String, PokemonAbility> pokemonAbilityCacheByName = pokemonAbilityRepository.findAll().stream()
                .collect(Collectors.toMap(PokemonAbility::getName, Function.identity()));

        savePokemonTypeMapping(pokemonSaveResponses, pokemonCacheByName, pokemonTypeCacheByName);
        savePokemonAbilityMapping(pokemonSaveResponses, pokemonCacheByName, pokemonAbilityCacheByName);
    }

    private void savePokemonsByOffset(
            int offset,
            List<Pokemon> pokemons,
            List<PokemonSaveResponse> pokemonSaveResponses
    ) {
        DataUrls pokemonDataUrls = pokeClient.getPokemonResponses(offset, PACKET_SIZE);
        for (DataUrl dataUrl : pokemonDataUrls.results()) {
            String id = dataUrl.getUrlId();
            PokemonSaveResponse pokemonSaveResponse = pokeClient.getPokemonSaveResponse(id);
            pokemonSaveResponses.add(pokemonSaveResponse);

            savePokemon(pokemonSaveResponse, id, pokemons);
        }
    }

    private void savePokemon(
            PokemonSaveResponse pokemonSaveResponse,
            String id,
            List<Pokemon> pokemons
    ) {
        PokemonDetail pokemonDetail = dtoParser.getPokemonDetails(pokemonSaveResponse);
        DataUrl species = pokemonDetail.species();
        PokemonNameAndDexNumber pokemonNameAndDexNumber = getPokemonNameAndDexNumber(species);
        String image;
        try {
            image = s3Service.postImageToS3(pokeClient.getImage(id));
        } catch (HttpClientErrorException e) {
            image = "이미지가 없습니다ㅠㅠ";
        }
        Pokemon pokemon = new Pokemon(
                pokemonNameAndDexNumber.pokedexNumber(),
                pokemonDetail.name(),
                pokemonNameAndDexNumber.koName(),
                pokemonDetail.weight(),
                pokemonDetail.height(),
                pokemonDetail.hp(),
                pokemonDetail.speed(),
                pokemonDetail.attack(),
                pokemonDetail.defense(),
                pokemonDetail.specialAttack(),
                pokemonDetail.specialDefense(),
                pokemonDetail.totalStats(),
                image);

        pokemons.add(pokemon);
    }

    private PokemonNameAndDexNumber getPokemonNameAndDexNumber(DataUrl species) {
        PokemonSpeciesResponse pokemonSpeciesResponse = pokeClient.getPokemonSpeciesResponse(species.getUrlId());
        return dtoParser.getPokemonNameAndDexNumber(pokemonSpeciesResponse);
    }

    private void savePokemonTypeMapping(
            List<PokemonSaveResponse> pokemonSaveResponses,
            Map<String, Pokemon> pokemonMap,
            Map<String, PokemonType> pokemonTypeCacheByName
    ) {
        List<PokemonTypeMapping> pokemonTypeMappings = pokemonSaveResponses.stream()
                .flatMap(pokemonSaveResponse -> pokemonSaveResponse.types().stream()
                        .map(typeDataUrl -> new PokemonTypeMapping(
                                pokemonMap.get(pokemonSaveResponse.name()),
                                pokemonTypeCacheByName.get(typeDataUrl.getName())
                        ))
                ).toList();
        pokemonTypeMappingRepository.saveAll(pokemonTypeMappings);
    }

    private void savePokemonAbilityMapping(
            List<PokemonSaveResponse> pokemonSaveResponses,
            Map<String, Pokemon> pokemonCacheByName,
            Map<String, PokemonAbility> pokemonAbilityCacheByName
    ) {
        List<PokemonAbilityMapping> pokemonAbilityMappings = pokemonSaveResponses.stream()
                .flatMap(pokemonSaveResponse -> pokemonSaveResponse.abilities().stream()
                        .map(abilityDataUrl -> new PokemonAbilityMapping(
                                pokemonCacheByName.get(pokemonSaveResponse.name()),
                                pokemonAbilityCacheByName.get(abilityDataUrl.getName())
                        ))
                ).toList();
        pokemonAbilityMappingRepository.saveAll(pokemonAbilityMappings);
    }
}
