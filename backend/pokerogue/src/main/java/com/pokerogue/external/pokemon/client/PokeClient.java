package com.pokerogue.external.pokemon.client;

import com.pokerogue.external.pokemon.dto.CountResponse;
import com.pokerogue.external.pokemon.dto.DataUrls;
import com.pokerogue.external.pokemon.dto.ability.AbilityResponse;
import com.pokerogue.external.pokemon.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.external.pokemon.dto.pokemon.species.PokemonSpeciesResponse;
import com.pokerogue.external.pokemon.dto.type.TypeMatchingResponse;
import com.pokerogue.external.pokemon.dto.type.TypeResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PokeClient {

    private final RestClient restClient;

    public PokeClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public CountResponse getAbilityResponsesCount() {
        return restClient.get()
                .uri("/ability")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(CountResponse.class);
    }

    public DataUrls getAbilityResponses(int limit) {
        return restClient.get()
                .uri("/ability/?offset=0&limit={limit}", limit)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DataUrls.class);
    }

    public AbilityResponse getAbilityResponse(String id) {
        return restClient.get()
                .uri("/ability/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(AbilityResponse.class);
    }

    public CountResponse getTypeResponsesCount() {
        return restClient.get()
                .uri("/type")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(CountResponse.class);
    }

    public DataUrls getTypeResponses(int limit) {
        return restClient.get()
                .uri("/type/?offset=0&limit={limit}", limit)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DataUrls.class);
    }

    public TypeResponse getTypeResponse(String id) {
        return restClient.get()
                .uri("/type/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(TypeResponse.class);
    }

    public CountResponse getPokemonResponsesCount() {
        return restClient.get()
                .uri("/pokemon")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(CountResponse.class);
    }

    public DataUrls getPokemonResponses(int offset, int limit) {
        return restClient.get()
                .uri("/pokemon/?offset={offset}&limit={limit}", offset, limit)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DataUrls.class);
    }

    public PokemonSaveResponse getPokemonSaveResponse(String id) {
        return restClient.get()
                .uri("/pokemon/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(PokemonSaveResponse.class);
    }

    public PokemonSpeciesResponse getPokemonSpeciesResponse(String id) {
        return restClient.get()
                .uri("/pokemon-species/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(PokemonSpeciesResponse.class);
    }

    public TypeMatchingResponse getTypeMatchingResponse(String id) {
        return restClient.get()
                .uri("/type/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(TypeMatchingResponse.class);
    }

    public byte[] getPixelImage(String id) {
        return restClient.get()
                .uri("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{id}.png", id)
                .accept(MediaType.IMAGE_PNG)
                .retrieve()
                .body(byte[].class);
    }

    public byte[] getArtImage(String id) {
        return restClient.get()
                .uri("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/{id}.png", id)
                .accept(MediaType.IMAGE_PNG)
                .retrieve()
                .body(byte[].class);
    }
}
