package com.pokerogue.helper.battle;

import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BattleController {

    private final BattleService battleService;

    @GetMapping("/api/v1/weathers")
    public ApiResponse<List<WeatherResponse>> weatherList() {
        return new ApiResponse<>("날씨 리스트 불러오기에 성공했습니다.", battleService.findWeathers());
    }

    @GetMapping("/api/v1/moves")
    public ApiResponse<List<MoveResponse>> moveByPokemonList(@RequestParam("pokedex-number") Integer pokedexNumber) {
        return new ApiResponse<>("포켓몬의 기술 리스트 불러오기에 성공했습니다.", battleService.findMovesByPokemon(pokedexNumber));
    }
}
