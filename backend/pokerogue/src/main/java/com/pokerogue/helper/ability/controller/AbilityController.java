package com.pokerogue.helper.ability.controller;

import com.pokerogue.helper.ability.dto.AbilityResponse;
import com.pokerogue.helper.ability.service.AbilityService;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AbilityController {

    private final AbilityService abilityService;

    @GetMapping("/api/v1/abilities")
    public ApiResponse<List<AbilityResponse>> abilityList() {
        return new ApiResponse<>("특성 리스트 불러오기에 성공했습니다.", abilityService.findAbilities());
    }
}
