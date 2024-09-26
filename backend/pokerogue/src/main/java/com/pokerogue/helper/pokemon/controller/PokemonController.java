package com.pokerogue.helper.pokemon.controller;


import com.pokerogue.helper.pokemon.dto.PokemonDetailResponse;
import com.pokerogue.helper.pokemon.dto.PokemonResponse;
import com.pokerogue.helper.pokemon.service.PokemonService;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping("/api/v1/pokemons2")
    public ApiResponse<List<PokemonResponse>> pokemonList() {
        return new ApiResponse<>("포켓몬 리스트 불러오기에 성공했습니다.", pokemonService.findAll());
    }

    @GetMapping("/api/v1/pokemon2/{id}")
    public ApiResponse<PokemonDetailResponse> pokemonDetails(@PathVariable("id") String id) {
        return new ApiResponse<>("포켓몬 정보 불러오기에 성공했습니다.", pokemonService.findById(id));
    }
}
