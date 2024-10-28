package com.pokerogue.helper.biome.controller;

import com.pokerogue.helper.biome.dto.BiomeDetailResponse;
import com.pokerogue.helper.biome.dto.BiomeResponse;
import com.pokerogue.helper.biome.service.BiomeService;
import com.pokerogue.helper.global.constant.SortingCriteria;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BiomeController {

    private final BiomeService biomeService;

    @GetMapping("/api/v1/biomes")
    public ApiResponse<List<BiomeResponse>> biomeList() {
        return new ApiResponse<>("바이옴 리스트 불러오기에 성공했습니다.", biomeService.findBiomes());
    }

    @GetMapping("/api/v1/biome/{id}")
    public ApiResponse<BiomeDetailResponse> biomeDetails(@PathVariable("id") String id,
                                                         @RequestParam(value = "boss", defaultValue = "desc") SortingCriteria bossPokemonOrder,
                                                         @RequestParam(value = "wild", defaultValue = "asc") SortingCriteria wildPokemonOrder) {
        log.info(
                "---- URI : {}, Param : {}, ThreadName : {}",
                "/api/v1/biome/{id}",
                id,
                Thread.currentThread().getName()
        );

        return new ApiResponse<>("바이옴 정보 불러오기에 성공했습니다.",
                biomeService.findBiome(id, bossPokemonOrder, wildPokemonOrder));
    }
}
