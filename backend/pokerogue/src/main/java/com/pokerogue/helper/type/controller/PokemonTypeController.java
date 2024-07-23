package com.pokerogue.helper.type.controller;

import com.pokerogue.helper.external.service.DataSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PokemonTypeController {

    private final DataSettingService dataSettingService;

    @PostMapping("/api/v1/setting/types")
    public void saveTypes() {
        dataSettingService.savePokemonTypes();
    }
}
