package com.pokerogue.helper.ability.controller;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.dto.PokemonAbilityResponse;
import com.pokerogue.helper.ability.service.PokemonAbilityService;
import com.pokerogue.helper.util.Saver;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PokemonAbilityController {

    private final PokemonAbilityService pokemonAbilityService;
    private final Saver saver;

    @GetMapping("/api/v1/abilities")
    public ApiResponse<List<PokemonAbilityResponse>> abilityList() {
        return new ApiResponse<>("특성 리스트 불러오기에 성공했습니다.", pokemonAbilityService.findAbilities());
    }

    @GetMapping("api/v1/update/ability")
    public ApiResponse<List<PokemonAbility>> saveAbilityList() {
        List<PokemonAbility> savedPokemonAbilities = saver.savePokemonAbilities();

        return new ApiResponse<>("특성 정보 업데이트", savedPokemonAbilities);
    }
}
