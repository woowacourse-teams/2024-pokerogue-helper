package com.pokerogue.helper.biome.controller;

import com.pokerogue.helper.biome.dto.BiomeResponse;
import com.pokerogue.helper.biome.service.BiomeService;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
}
