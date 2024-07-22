package com.pokerogue.helper.external.client;

import com.pokerogue.helper.external.dto.CountResponse;
import com.pokerogue.helper.external.dto.ListResponse;
import com.pokerogue.helper.external.dto.ability.AbilityResponse;
import com.pokerogue.helper.external.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.helper.external.dto.pokemon.species.PokemonSpeciesResponse;
import com.pokerogue.helper.external.dto.type.TypeResponse;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

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

    public ListResponse getAbilityResponses(String limit) {
        return restClient.get()
                .uri("/ability/?offset=0&limit={limit}", limit)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ListResponse.class);
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

    public ListResponse getTypeResponses(String limit) {
        return restClient.get()
                .uri("/type/?offset=0&limit={limit}", limit)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ListResponse.class);
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

    public ListResponse getPokemonResponses(String offset, String limit) {
        return restClient.get()
                .uri("/pokemon/?offset={offset}&limit={limit}", offset, limit)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ListResponse.class);
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
}
