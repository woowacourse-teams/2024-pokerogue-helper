package com.pokerogue.helper.pokemon.controller;

import com.pokerogue.helper.pokemon.dto.PokedexResponse;
import com.pokerogue.helper.pokemon.dto.PokemonResponse;
import com.pokerogue.helper.pokemon.service.PokemonService;
import com.pokerogue.external.pokemon.service.DataSettingService;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;
    private final DataSettingService dataSettingService;

    @GetMapping("/api/v1/pokemons")
    public ApiResponse<List<PokemonResponse>> pokemonList() {
        return new ApiResponse<>("포켓몬 리스트 불러오기에 성공했습니다.", pokemonService.findPokemons());
    }

    @GetMapping("/api/v1/pokemon/{id}")
    public ApiResponse<PokedexResponse> pokedexDetails(@PathVariable("id") Long id) {
        return new ApiResponse<>("포켓몬 정보 불러오기에 성공했습니다.", pokemonService.findPokedexDetails(id));
    }

    @PostMapping("/api/v1/setting")
    public ApiResponse<Void> savePokemons() {
        dataSettingService.setData();

        return new ApiResponse<>("포켓몬 데이터 저장에 성공했습니다.", null);
    }
}
