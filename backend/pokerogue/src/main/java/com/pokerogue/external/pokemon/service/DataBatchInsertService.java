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
import com.pokerogue.external.s3.service.S3Service;
import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.repository.JDBCPokemonAbilityRepository;
import com.pokerogue.helper.ability.repository.PokemonAbilityRepository;
import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import com.pokerogue.helper.pokemon.repository.JDBCPokemonAbilityMappingRepository;
import com.pokerogue.helper.pokemon.repository.JDBCPokemonRepository;
import com.pokerogue.helper.pokemon.repository.JDBCPokemonTypeMappingRepository;
import com.pokerogue.helper.pokemon.repository.PokemonAbilityMappingRepository;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import com.pokerogue.helper.pokemon.repository.PokemonTypeMappingRepository;
import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.type.domain.PokemonTypeMatching;
import com.pokerogue.helper.type.repository.JDBCPokemonTypeMatchingRepository;
import com.pokerogue.helper.type.repository.JDBCPokemonTypeRepository;
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
public class DataBatchInsertService {

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
    private final JDBCPokemonAbilityMappingRepository jdbcPokemonAbilityMappingRepository;
    private final JDBCPokemonTypeMappingRepository jdbcPokemonTypeMappingRepository;
    private final JDBCPokemonTypeMatchingRepository jdbcPokemonTypeMatchingRepository;
    private final JDBCPokemonTypeRepository jdbcPokemonTypeRepository;
    private final JDBCPokemonAbilityRepository jdbcPokemonAbilityRepository;
    private final JDBCPokemonRepository jdbcPokemonRepository;

    @Transactional
    public void setDataBatch() {
        deleteAll();

        savePokemonTypesTestBatch();
        savePokemonAbilitiesTestBatch();
        savePokemonsTestBatch();
    }

    private void deleteAll() {
        pokemonTypeMappingRepository.deleteAllInBatch();
        pokemonAbilityMappingRepository.deleteAllInBatch();
        pokemonTypeMatchingRepository.deleteAllInBatch();
        pokemonTypeRepository.deleteAllInBatch();
        pokemonAbilityRepository.deleteAllInBatch();
        pokemonRepository.deleteAllInBatch();
    }

    @Transactional
    public void setDataTestTypeBatch() {
        pokemonTypeMappingRepository.deleteAllInBatch();
        pokemonTypeMatchingRepository.deleteAllInBatch();
        pokemonTypeRepository.deleteAllInBatch();
        savePokemonTypesTestBatch();
    }

    private void savePokemonTypesTestBatch() {
        DataUrls typeDataUrls = getTypeDataUrls();
        List<TypeResponse> typeResponses = getTypeResponses(typeDataUrls);
        List<PokemonType> pokemonTypes = getPokemonTypes(typeResponses);

        jdbcPokemonTypeRepository.batchInsertPokemonType(pokemonTypes);
        saveAllPokemonTypeMatchingTestBatch(typeDataUrls);
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

    private void saveAllPokemonTypeMatchingTestBatch(DataUrls typeDataUrls) {
        Map<String, PokemonType> pokemonTypeMap = pokemonTypeRepository.findAll().stream()
                .collect(Collectors.toMap(PokemonType::getName, Function.identity()));
        List<PokemonTypeMatching> pokemonTypeMatchings = new ArrayList<>();
        for (DataUrl dataUrl : typeDataUrls.results()) {
            PokemonType pokemonType = pokemonTypeRepository.findByName(dataUrl.name()).orElseThrow();
            TypeMatchingResponse typeMatchingResponse = pokeClient.getTypeMatchingResponse(dataUrl.getUrlId());

            savePokemonTypeMatchingTestBatch(typeMatchingResponse, pokemonType, pokemonTypeMatchings, pokemonTypeMap);
        }
        jdbcPokemonTypeMatchingRepository.batchInsertPokemonTypeMatching(pokemonTypeMatchings);
    }

    private void savePokemonTypeMatchingTestBatch(
            TypeMatchingResponse typeMatchingResponse,
            PokemonType fromPokemonType,
            List<PokemonTypeMatching> pokemonTypeMatchings,
            Map<String, PokemonType> pokemonTypeMap
    ) {
        List<PokemonType> allPokemonTypes = pokemonTypeRepository.findAll();

        saveDoubleDamageTypeMatchingTestBatch(
                typeMatchingResponse,
                fromPokemonType,
                allPokemonTypes,
                pokemonTypeMatchings,
                pokemonTypeMap);
        saveHalfDamageTypeMatchingTestBatch(
                typeMatchingResponse,
                fromPokemonType,
                allPokemonTypes,
                pokemonTypeMatchings,
                pokemonTypeMap);
        saveNoDamageTypeMatchingTestBatch(
                typeMatchingResponse,
                fromPokemonType,
                allPokemonTypes,
                pokemonTypeMatchings,
                pokemonTypeMap);
        saveBasicDamageTypeMatchingTestBatch(fromPokemonType, allPokemonTypes, pokemonTypeMatchings);
    }

    private void saveDoubleDamageTypeMatchingTestBatch(
            TypeMatchingResponse typeMatchingResponse,
            PokemonType fromPokemonType,
            List<PokemonType> allPokemonTypes,
            List<PokemonTypeMatching> pokemonTypeMatchings,
            Map<String, PokemonType> pokemonTypeMap
    ) {
        for (DataUrl type : typeMatchingResponse.getDoubleDamageTo()) {
            PokemonType toPokemonType = pokemonTypeMap.get(type.name());
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromPokemonType, toPokemonType, DOUBLE_DAMAGE);

            pokemonTypeMatchings.add(pokemonTypeMatching);
            allPokemonTypes.remove(toPokemonType);
        }
    }

    private void saveHalfDamageTypeMatchingTestBatch(
            TypeMatchingResponse typeMatchingResponse,
            PokemonType fromPokemonType,
            List<PokemonType> allPokemonTypes,
            List<PokemonTypeMatching> pokemonTypeMatchings,
            Map<String, PokemonType> pokemonTypeMap
    ) {
        for (DataUrl type : typeMatchingResponse.getHalfDamageTo()) {
            PokemonType toPokemonType = pokemonTypeMap.get(type.name());
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromPokemonType, toPokemonType, HALF_DAMAGE);

            pokemonTypeMatchings.add(pokemonTypeMatching);
            allPokemonTypes.remove(toPokemonType);
        }
    }

    private void saveNoDamageTypeMatchingTestBatch(
            TypeMatchingResponse typeMatchingResponse,
            PokemonType fromPokemonType,
            List<PokemonType> allPokemonTypes,
            List<PokemonTypeMatching> pokemonTypeMatchings,
            Map<String, PokemonType> pokemonTypeMap
    ) {
        for (DataUrl type : typeMatchingResponse.getNoDamageTo()) {
            PokemonType toPokemonType = pokemonTypeMap.get(type.name());
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromPokemonType, toPokemonType, NO_DAMAGE);

            pokemonTypeMatchings.add(pokemonTypeMatching);
            allPokemonTypes.remove(toPokemonType);
        }
    }

    private void saveBasicDamageTypeMatchingTestBatch(
            PokemonType fromPokemonType,
            List<PokemonType> allPokemonTypes,
            List<PokemonTypeMatching> pokemonTypeMatchings
    ) {
        for (PokemonType toPokemonType : allPokemonTypes) {
            PokemonTypeMatching pokemonTypeMatching = new PokemonTypeMatching(fromPokemonType, toPokemonType, BASIC_DAMAGE);

            pokemonTypeMatchings.add(pokemonTypeMatching);
        }
    }

    @Transactional
    public void setDataTestAbilityBatch() {
        pokemonAbilityMappingRepository.deleteAllInBatch();
        pokemonAbilityRepository.deleteAllInBatch();
        savePokemonAbilitiesTestBatch();
    }

    private void savePokemonAbilitiesTestBatch() {
        DataUrls abilityDataUrls = getAbilityDataUrls();
        List<AbilityResponse> abilityResponses = getAbilityResponses(abilityDataUrls);
        List<PokemonAbility> pokemonAbilities = getPokemonAbilities(abilityResponses);

        jdbcPokemonAbilityRepository.batchInsertPokemonAbility(pokemonAbilities);
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

    @Transactional
    public void setDataTestPokemonBatch() {
        pokemonRepository.deleteAllInBatch();
        savePokemonsTestBatch();
    }

    private void savePokemonsTestBatch() {
        List<Pokemon> pokemons = new ArrayList<>();
        List<PokemonSaveResponse> pokemonSaveResponses = new ArrayList<>();
        CountResponse pokemonCountResponse = pokeClient.getPokemonResponsesCount();
        for (int offset = 0; offset < pokemonCountResponse.count(); offset += PACKET_SIZE) {
            savePokemonsByOffsetTestBatch(offset, pokemons);
        }
        jdbcPokemonRepository.batchInsertPokemon(pokemons);
        Map<String, Pokemon> pokemonMap = pokemonRepository.findAll().stream()
                .collect(Collectors.toMap(Pokemon::getName, Function.identity()));
        Map<String, PokemonType> pokemonTypeMap = pokemonTypeRepository.findAll().stream()
                .collect(Collectors.toMap(PokemonType::getName, Function.identity()));
        Map<String, PokemonAbility> pokemonAbilityMap = pokemonAbilityRepository.findAll().stream()
                .collect(Collectors.toMap(PokemonAbility::getName, Function.identity()));

        savePokemonTypeMappingTestBatch(pokemonSaveResponses, pokemonMap, pokemonTypeMap);
        savePokemonAbilityMappingTestBatch(pokemonSaveResponses, pokemonMap, pokemonAbilityMap);
    }

    private void savePokemonsByOffsetTestBatch(int offset, List<Pokemon> pokemons) {
        DataUrls pokemonDataUrls = pokeClient.getPokemonResponses(offset, PACKET_SIZE);
        for (DataUrl dataUrl : pokemonDataUrls.results()) {
            String id = dataUrl.getUrlId();
            PokemonSaveResponse pokemonSaveResponse = pokeClient.getPokemonSaveResponse(id);

            savePokemonTestBatch(pokemonSaveResponse, id, pokemons);
        }
    }

    private void savePokemonTestBatch(PokemonSaveResponse pokemonSaveResponse, String id, List<Pokemon> pokemons) {
        PokemonDetail pokemonDetail = dtoParser.getPokemonDetails(pokemonSaveResponse);
        DataUrl species = pokemonDetail.species();
        PokemonNameAndDexNumber pokemonNameAndDexNumber = getPokemonNameAndDexNumber(
                getPokemonSpeciesResponse(species));
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

    private PokemonSpeciesResponse getPokemonSpeciesResponse(DataUrl species) {
        return pokeClient.getPokemonSpeciesResponse(species.getUrlId());
    }

    private PokemonNameAndDexNumber getPokemonNameAndDexNumber(PokemonSpeciesResponse pokemonSpeciesResponse) {
        return dtoParser.getPokemonNameAndDexNumber(pokemonSpeciesResponse);
    }

    private void savePokemonTypeMappingTestBatch(
            List<PokemonSaveResponse> pokemonSaveResponses,
            Map<String, Pokemon> pokemonMap,
            Map<String, PokemonType> pokemonTypeMap
    ) {
        List<PokemonTypeMapping> pokemonTypeMappings = new ArrayList<>();
        for (PokemonSaveResponse pokemonSaveResponse : pokemonSaveResponses) {
            List<TypeDataUrl> types = pokemonSaveResponse.types();
            for (TypeDataUrl typeDataUrl : types) {
                String name = typeDataUrl.getName();
                Pokemon pokemon = pokemonMap.get(pokemonSaveResponse.name());
                PokemonType pokemonType = pokemonTypeMap.get(name);
                PokemonTypeMapping pokemonTypeMapping = new PokemonTypeMapping(pokemon, pokemonType);
                pokemonTypeMappings.add(pokemonTypeMapping);
            }
        }

        jdbcPokemonTypeMappingRepository.batchInsertPokemonTypeMapping(pokemonTypeMappings);
    }

    private void savePokemonAbilityMappingTestBatch(
            List<PokemonSaveResponse> pokemonSaveResponses,
            Map<String, Pokemon> pokemonMap,
            Map<String, PokemonAbility> pokemonAbilityMap
    ) {
        List<PokemonAbilityMapping> pokemonAbilityMappings = new ArrayList<>();
        for (PokemonSaveResponse pokemonSaveResponse : pokemonSaveResponses) {
            List<AbilityDataUrl> abilities = pokemonSaveResponse.abilities();
            for (AbilityDataUrl abilityDataUrl : abilities) {
                String name = abilityDataUrl.getName();
                Pokemon pokemon = pokemonMap.get(pokemonSaveResponse.name());
                PokemonAbility pokemonAbility = pokemonAbilityMap.get(name);
                PokemonAbilityMapping pokemonAbilityMapping = new PokemonAbilityMapping(pokemon, pokemonAbility);
                pokemonAbilityMappings.add(pokemonAbilityMapping);
            }
        }

        jdbcPokemonAbilityMappingRepository.batchInsertPokemonAbilityMapping(pokemonAbilityMappings);
    }
}