package com.pokerogue.helper.type.controller;

import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.util.DataSettingService;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PokemonTypeController {

    private final DataSettingService dataSettingService;

    @GetMapping("/api/v1/update/type")
    public ApiResponse<List<PokemonType>> saveTypeList() {
        List<PokemonType> savedPokemonTypes = dataSettingService.savePokemonTypes();

        return new ApiResponse<>("타입 정보 업데이트", savedPokemonTypes);
    }
}
