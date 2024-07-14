package com.pokerogue.helper.ability.controller;

import com.pokerogue.helper.ability.dto.AbilityResponse;
import com.pokerogue.helper.ability.service.AbilityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AbilityController {

    private final AbilityService abilityService;

    @GetMapping("/api/v1/abilities")
    public List<AbilityResponse> abilityList() {
        return abilityService.findAbilities();
    }
}
