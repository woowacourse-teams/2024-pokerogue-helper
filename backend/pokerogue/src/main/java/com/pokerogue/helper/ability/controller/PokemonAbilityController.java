package com.pokerogue.helper.ability.controller;

import com.pokerogue.helper.ability.dto.PokemonAbilityResponse;
import com.pokerogue.helper.ability.dto.PokemonAbilityWithPokemonsResponse;
import com.pokerogue.helper.ability.service.PokemonAbilityService;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PokemonAbilityController {

    private final PokemonAbilityService pokemonAbilityService;

    @GetMapping("/api/v1/abilities")
    public ApiResponse<List<PokemonAbilityResponse>> abilityList() {
        return new ApiResponse<>("특성 리스트 불러오기에 성공했습니다.", pokemonAbilityService.findAbilities());
    }

    @GetMapping("/api/v1/ability/{id}")
    public ApiResponse<PokemonAbilityWithPokemonsResponse> abilityDetails(@PathVariable("id") Long id) {
        log.info("---- URI : {}, Param : {}", "/api/v1/ability/{id}", id);

        return new ApiResponse<>("특성 정보 불러오기에 성공했습니다.", pokemonAbilityService.findAbilityDetails(id));
    }
}
