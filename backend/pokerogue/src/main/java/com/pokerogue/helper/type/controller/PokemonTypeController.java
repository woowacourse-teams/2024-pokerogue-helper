package com.pokerogue.helper.type.controller;

import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.util.DataSettingService;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PokemonTypeController {

    private final DataSettingService dataSettingService;

    @PostMapping("/api/v1/setting/types")
    public ApiResponse<List<PokemonType>> saveTypes() {
        List<PokemonType> savedPokemonTypes = dataSettingService.savePokemonTypes();

        return new ApiResponse<>("타입 정보 업데이트", savedPokemonTypes);
    }
}
