package com.pokerogue.helper.type.controller;

import com.pokerogue.helper.type.service.PokemonTypeService;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PokemonTypeController {

    private final PokemonTypeService pokemonTypeService;

    @GetMapping("/api/v1/types")
    public ApiResponse<List<String>> typeList() {
        return new ApiResponse<>("타입 리스트 불러오기에 성공했습니다.", pokemonTypeService.findTypes());
    }

    @GetMapping("/api/v1/types/matching")
    public ApiResponse<String> matchingAndTypeList() {
        return new ApiResponse<>("타입 상성 리스트 불러오기에 성공했습니다.", pokemonTypeService.findMatchingAndTypes());
    }
}
