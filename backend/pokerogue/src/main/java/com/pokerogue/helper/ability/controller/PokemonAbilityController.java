package com.pokerogue.helper.ability.controller;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import com.pokerogue.helper.ability.dto.PokemonAbilityResponse;
import com.pokerogue.helper.ability.service.PokemonAbilityService;
import com.pokerogue.helper.external.client.PokeClient;
import com.pokerogue.helper.external.dto.CountResponse;
import com.pokerogue.helper.external.dto.ListResponse;
import com.pokerogue.helper.external.dto.NameAndUrl;
import com.pokerogue.helper.external.dto.ability.AbilityResponse;
import com.pokerogue.helper.util.Saver;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PokemonAbilityController {

    private final PokemonAbilityService pokemonAbilityService;
    private final Saver saver;
    private final PokeClient pokeClient;

    @GetMapping("/api/v1/abilities")
    public ApiResponse<List<PokemonAbilityResponse>> abilityList() {
        return new ApiResponse<>("특성 리스트 불러오기에 성공했습니다.", pokemonAbilityService.findAbilities());
    }

    @GetMapping("api/v1/update/ability")
    public ApiResponse<List<PokemonAbility>> saveAbilityList() {
        CountResponse abilityListSize = pokeClient.getAbilityListSize();
        ListResponse abilityList = pokeClient.getAbilityList(String.valueOf(abilityListSize.count()));
        List<AbilityResponse> abilityResponses = new ArrayList<>();
        for (NameAndUrl nameAndUrl : abilityList.results()) {
            String[] split = nameAndUrl.url().split("/");
            AbilityResponse abilityResponse = pokeClient.getAbilityResponse(split[split.length - 1]);
            abilityResponses.add(abilityResponse);
        }
        List<PokemonAbility> savedPokemonAbilities = saver.savePokemonAbilityList(abilityResponses);
        return new ApiResponse<>("특성 정보 업데이트", savedPokemonAbilities);
    }
}
