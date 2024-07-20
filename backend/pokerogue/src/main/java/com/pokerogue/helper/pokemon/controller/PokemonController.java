package com.pokerogue.helper.pokemon.controller;

import com.pokerogue.helper.external.client.PokeClient;
import com.pokerogue.helper.external.dto.CountResponse;
import com.pokerogue.helper.external.dto.ListResponse;
import com.pokerogue.helper.external.dto.NameAndUrl;
import com.pokerogue.helper.external.dto.pokemon.PokemonSaveResponse;
import com.pokerogue.helper.pokemon.domain.Pokemon;
import com.pokerogue.helper.pokemon.dto.PokedexResponse;
import com.pokerogue.helper.pokemon.dto.PokemonResponse;
import com.pokerogue.helper.pokemon.service.PokemonService;
import com.pokerogue.helper.util.Saver;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;
    private final PokeClient pokeClient;
    private final Saver saver;

    @GetMapping("/api/v1/pokemons")
    public ApiResponse<List<PokemonResponse>> pokemonList() {
        return new ApiResponse<>("포켓몬 리스트 불러오기에 성공했습니다.", pokemonService.findPokemons());
    }

    @GetMapping("/api/v1/pokemon/{id}")
    public ApiResponse<PokedexResponse> pokedexDetails(@PathVariable("id") Long id) {
        return new ApiResponse<>("포켓몬 정보 불러오기에 성공했습니다.", pokemonService.findPokedexDetails(id));
    }

    @GetMapping("/api/v1/update/pokemon")
    public ApiResponse<List<Pokemon>> savePokemonList() {
        CountResponse pokemonListSize = pokeClient.getPokemonListSize();
        ListResponse pokemonList = pokeClient.getPokemonList(String.valueOf(pokemonListSize.count()));
        List<Pokemon> pokemons = new ArrayList<>();
        for (NameAndUrl nameAndUrl : pokemonList.results()) {
            String[] split = nameAndUrl.url().split("/");
            PokemonSaveResponse pokemonSaveResponse = pokeClient.getPokemonSaveResponse(split[split.length - 1]);
            Pokemon pokemon = saver.savePokemon(pokemonSaveResponse);
            pokemons.add(pokemon);
        }
        return new ApiResponse<>("포켓몬 저장 완료", pokemons);
    }
}
