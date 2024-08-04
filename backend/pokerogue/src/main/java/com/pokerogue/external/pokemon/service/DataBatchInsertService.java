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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Slf4j
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

    private void savePokemonTypesTestBatch() {
        log.info("********타입 API 정보 수신 및 가공 시작********");
        DataUrls typeDataUrls = getTypeDataUrls();
        List<TypeResponse> typeResponses = getTypeResponses(typeDataUrls);
        List<PokemonType> pokemonTypes = getPokemonTypes(typeResponses);
        log.info("********타입 API 정보 수신 및 가공 종료********");

        log.info("********타입 Batch Insert 시작********");
        jdbcPokemonTypeRepository.batchInsertPokemonType(pokemonTypes);
        log.info("********타입 Batch Insert 종료********");
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
        log.info("********타입 상성 API 정보 수신 및 가공 시작********");
        Map<String, PokemonType> pokemonTypeMap = pokemonTypeRepository.findAll().stream()
                .collect(Collectors.toMap(PokemonType::getName, Function.identity()));
        List<PokemonTypeMatching> pokemonTypeMatchings = new ArrayList<>();
        for (DataUrl dataUrl : typeDataUrls.results()) {
            PokemonType pokemonType = pokemonTypeRepository.findByName(dataUrl.name()).orElseThrow();
            TypeMatchingResponse typeMatchingResponse = pokeClient.getTypeMatchingResponse(dataUrl.getUrlId());

            savePokemonTypeMatchingTestBatch(typeMatchingResponse, pokemonType, pokemonTypeMatchings, pokemonTypeMap);
        }
        log.info("********타입 상성 API 정보 수신 및 가공 종료********");
        log.info("********타입 상성 Batch Insert 시작********");
        jdbcPokemonTypeMatchingRepository.batchInsertPokemonTypeMatching(pokemonTypeMatchings);
        log.info("********타입 상성 Batch Insert 종료********");
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

    private void savePokemonAbilitiesTestBatch() {
        log.info("********특성 API 정보 수신 및 가공 시작********");
        DataUrls abilityDataUrls = getAbilityDataUrls();
        List<AbilityResponse> abilityResponses = getAbilityResponses(abilityDataUrls);
        List<PokemonAbility> pokemonAbilities = getPokemonAbilities(abilityResponses);
        log.info("********특성 API 정보 수신 및 가공 종료********");

        log.info("********특성 Batch Insert 시작********");
        jdbcPokemonAbilityRepository.batchInsertPokemonAbility(pokemonAbilities);
        log.info("********특성 Batch Insert 종료********");
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

    private void savePokemonsTestBatch() {
        log.info("********포켓몬 API 정보 수신 및 가공 시작********");
        List<Pokemon> pokemons = new ArrayList<>();
        List<PokemonSaveResponse> pokemonSaveResponses = new ArrayList<>();
        CountResponse pokemonCountResponse = pokeClient.getPokemonResponsesCount();
        for (int offset = 0; offset < pokemonCountResponse.count(); offset += PACKET_SIZE) {
            savePokemonsByOffsetTestBatch(offset, pokemons, pokemonSaveResponses);
        }
        log.info("********포켓몬 API 정보 수신 및 가공 종료********");
        log.info("********포켓몬 Batch Insert 시작********");
        jdbcPokemonRepository.batchInsertPokemon(pokemons);
        log.info("********포켓몬 Batch Insert 종료********");
        log.info("********포켓몬, 타입, 특성 정보 캐싱 시작********");
        Map<String, Pokemon> pokemonMap = pokemonRepository.findAll().stream()
                .collect(Collectors.toMap(Pokemon::getName, Function.identity()));
        Map<String, PokemonType> pokemonTypeMap = pokemonTypeRepository.findAll().stream()
                .collect(Collectors.toMap(PokemonType::getName, Function.identity()));
        Map<String, PokemonAbility> pokemonAbilityMap = pokemonAbilityRepository.findAll().stream()
                .collect(Collectors.toMap(PokemonAbility::getName, Function.identity()));
        log.info("********포켓몬, 타입, 특성 정보 캐싱 종료********");

        savePokemonTypeMappingTestBatch(pokemonSaveResponses, pokemonMap, pokemonTypeMap);
        savePokemonAbilityMappingTestBatch(pokemonSaveResponses, pokemonMap, pokemonAbilityMap);
    }

    private void savePokemonsByOffsetTestBatch(int offset, List<Pokemon> pokemons, List<PokemonSaveResponse> pokemonSaveResponses) {
        DataUrls pokemonDataUrls = pokeClient.getPokemonResponses(offset, PACKET_SIZE);
        for (DataUrl dataUrl : pokemonDataUrls.results()) {
            String id = dataUrl.getUrlId();
            PokemonSaveResponse pokemonSaveResponse = pokeClient.getPokemonSaveResponse(id);
            pokemonSaveResponses.add(pokemonSaveResponse);

            savePokemonTestBatch(pokemonSaveResponse, id, pokemons);
        }
    }

    private void savePokemonTestBatch(PokemonSaveResponse pokemonSaveResponse, String id, List<Pokemon> pokemons) {
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

    private void savePokemonTypeMappingTestBatch(
            List<PokemonSaveResponse> pokemonSaveResponses,
            Map<String, Pokemon> pokemonMap,
            Map<String, PokemonType> pokemonTypeMap
    ) {
        log.info("********포켓몬 타입 매핑 정보 가공 시작********");
        List<PokemonTypeMapping> pokemonTypeMappings = pokemonSaveResponses.stream()
                .flatMap(pokemonSaveResponse -> pokemonSaveResponse.types().stream()
                        .map(typeDataUrl -> new PokemonTypeMapping(
                                pokemonMap.get(pokemonSaveResponse.name()),
                                pokemonTypeMap.get(typeDataUrl.getName())
                        ))
                ).toList();
        log.info("********포켓몬 타입 매핑 정보 가공 종료********");
        log.info("********포켓몬 타입 매핑 Batch Insert 시작********");
        jdbcPokemonTypeMappingRepository.batchInsertPokemonTypeMapping(pokemonTypeMappings);
        log.info("********포켓몬 타입 매핑 Batch Insert 종료********");
    }

    private void savePokemonAbilityMappingTestBatch(
            List<PokemonSaveResponse> pokemonSaveResponses,
            Map<String, Pokemon> pokemonMap,
            Map<String, PokemonAbility> pokemonAbilityMap
    ) {
        log.info("********포켓몬 특성 매핑 정보 가공 시작********");
        List<PokemonAbilityMapping> pokemonAbilityMappings = pokemonSaveResponses.stream()
                .flatMap(pokemonSaveResponse -> pokemonSaveResponse.abilities().stream()
                        .map(abilityDataUrl -> new PokemonAbilityMapping(
                                pokemonMap.get(pokemonSaveResponse.name()),
                                pokemonAbilityMap.get(abilityDataUrl.getName())
                        ))
                ).toList();
        log.info("********포켓몬 특성 매핑 정보 가공 종료********");
        log.info("********포켓몬 특성 매핑 Batch Insert 시작********");
        jdbcPokemonAbilityMappingRepository.batchInsertPokemonAbilityMapping(pokemonAbilityMappings);
        log.info("********포켓몬 특성 매핑 Batch Insert 종료********");
    }
}
