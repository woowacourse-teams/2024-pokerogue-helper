package com.pokerogue.environment.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokerogue.external.pokemon.client.PokeClient;
import com.pokerogue.external.pokemon.dto.CountResponse;
import com.pokerogue.external.pokemon.dto.DataUrl;
import com.pokerogue.external.pokemon.dto.DataUrls;
import com.pokerogue.external.pokemon.dto.ability.AbilityResponse;
import com.pokerogue.external.pokemon.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.external.pokemon.dto.pokemon.species.PokemonSpeciesResponse;
import com.pokerogue.external.pokemon.dto.type.TypeMatchingResponse;
import com.pokerogue.external.pokemon.dto.type.TypeResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class FakePokeClient extends PokeClient {

    private static final List<DataUrl> abilityDataUrls = List.of(
            new DataUrl("stench", "https://pokeapi.co/api/v2/ability/1/"),
            new DataUrl("drizzle", "https://pokeapi.co/api/v2/ability/2/"),
            new DataUrl("speed-boost", "https://pokeapi.co/api/v2/ability/3/"),
            new DataUrl("damp", "https://pokeapi.co/api/v2/ability/6/"),
            new DataUrl("water-absorb", "https://pokeapi.co/api/v2/ability/11/"),
            new DataUrl("compound-eyes", "https://pokeapi.co/api/v2/ability/14/"),
            new DataUrl("chlorophyll", "https://pokeapi.co/api/v2/ability/34/"),
            new DataUrl("sticky-hold", "https://pokeapi.co/api/v2/ability/60/"),
            new DataUrl("blaze", "https://pokeapi.co/api/v2/ability/66/"),
            new DataUrl("frisk", "https://pokeapi.co/api/v2/ability/119/"),
            new DataUrl("poison-touch", "https://pokeapi.co/api/v2/ability/143/")
    );
    private static final List<DataUrl> pokemonDataUrls = List.of(
            new DataUrl("gloom", "https://pokeapi.co/api/v2/pokemon/44/"),
            new DataUrl("grimer", "https://pokeapi.co/api/v2/pokemon/88/"),
            new DataUrl("politoed", "https://pokeapi.co/api/v2/pokemon/186/"),
            new DataUrl("kyogre", "https://pokeapi.co/api/v2/pokemon/382/"),
            new DataUrl("yanma", "https://pokeapi.co/api/v2/pokemon/193/"),
            new DataUrl("torchic", "https://pokeapi.co/api/v2/pokemon/255/")
    );
    private static final String JSON_PATH = "src/test/resources/json/";
    private static final String ABILITY_JSON_PATH = JSON_PATH + "ability/ability-%s.json";
    private static final String POKEMON_JSON_PATH = JSON_PATH + "pokemon/pokemon-%s.json";
    private static final String POKEMON_SPECIES_JSON_PATH = JSON_PATH + "pokemon-species/pokemon-species-%s.json";
    private static final String TYPE_JSON_PATH = JSON_PATH + "type/type-%s.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    public static final int TEST_POKEMON_COUNT;
    public static final int TEST_ABILITY_COUNT;
    public static final int TEST_TYPE_COUNT = 19;

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TEST_POKEMON_COUNT = pokemonDataUrls.size();
        TEST_ABILITY_COUNT = abilityDataUrls.size();
    }

    public FakePokeClient() {
        super(null);
    }

    @Override
    public CountResponse getAbilityResponsesCount() {
        return new CountResponse(TEST_ABILITY_COUNT);
    }

    @Override
    public DataUrls getAbilityResponses(int limit) {
        return new DataUrls(abilityDataUrls);
    }

    @Override
    public AbilityResponse getAbilityResponse(String id) {
        String path = String.format(ABILITY_JSON_PATH, id);

        return deserializeTestFixture(AbilityResponse.class, path);
    }

    @Override
    public CountResponse getTypeResponsesCount() {
        return new CountResponse(TEST_TYPE_COUNT);
    }

    @Override
    public DataUrls getTypeResponses(int limit) {
        String path = String.format(TYPE_JSON_PATH, "all");

        return deserializeTestFixture(DataUrls.class, path);
    }

    @Override
    public TypeResponse getTypeResponse(String id) {
        String path = String.format(TYPE_JSON_PATH, id);

        return deserializeTestFixture(TypeResponse.class, path);
    }

    @Override
    public CountResponse getPokemonResponsesCount() {
        return new CountResponse(TEST_POKEMON_COUNT);
    }

    @Override
    public DataUrls getPokemonResponses(int offset, int limit) {
        return new DataUrls(pokemonDataUrls);
    }

    @Override
    public PokemonSaveResponse getPokemonSaveResponse(String id) {
        String path = String.format(POKEMON_JSON_PATH, id);

        return deserializeTestFixture(PokemonSaveResponse.class, path);
    }

    @Override
    public PokemonSpeciesResponse getPokemonSpeciesResponse(String id) {
        String path = String.format(POKEMON_SPECIES_JSON_PATH, id);

        return deserializeTestFixture(PokemonSpeciesResponse.class, path);
    }

    @Override
    public TypeMatchingResponse getTypeMatchingResponse(String id) {
        String path = String.format(TYPE_JSON_PATH, id);

        return deserializeTestFixture(TypeMatchingResponse.class, path);
    }

    @Override
    public byte[] getPixelImage(String id) {
        return null;
    }

    @Override
    public byte[] getArtImage(String id) {
        return null;
    }

    private <T> T deserializeTestFixture(Class<T> dtoType, String path) {
        try {
            return mapper.readValue(new File(path), dtoType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("해당하는 json 파일을 찾을 수 없습니다.");
        } catch (Exception e) {
            throw new RuntimeException("테스트 데이터 역직렬화에 실패했습니다.");
        }
    }
}
