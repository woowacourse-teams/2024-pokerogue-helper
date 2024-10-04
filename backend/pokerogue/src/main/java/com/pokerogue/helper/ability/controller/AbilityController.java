package com.pokerogue.helper.ability.controller;

import com.pokerogue.helper.ability.dto.AbilityDetailResponse;
import com.pokerogue.helper.ability.dto.AbilityResponse;
import com.pokerogue.helper.ability.service.AbilityService;
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
public class AbilityController {

    private final AbilityService abilityService;

    @GetMapping("/api/v1/abilities2")
    public ApiResponse<List<AbilityResponse>> abilityList() {
        return new ApiResponse<>("특성 리스트 불러오기에 성공했습니다.", abilityService.findAbilities());
    }

    @GetMapping("/api/v1/ability2/{id}")
    public ApiResponse<AbilityDetailResponse> abilityDetails(@PathVariable("id") String id) {
        log.info(
                "---- URI : {}, Param : {}, ThreadName : {}",
                "/api/v1/ability/{id}",
                id,
                Thread.currentThread().getName()
        );

        return new ApiResponse<>("특성 정보 불러오기에 성공했습니다.", abilityService.findAbilityDetails(id));
    }
}
