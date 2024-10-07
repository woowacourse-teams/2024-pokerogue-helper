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

    @GetMapping("/api/v1/battle")
    public ApiResponse<BattleResultResponse> battleResult(@RequestParam("weather-id") String weatherId,
                                                          @RequestParam("my-pokemon-id") String myPokemonId,
                                                          @RequestParam("rival-pokemon-id") String rivalPokemonId,
                                                          @RequestParam("my-move-id") String myMoveId) {
        return new ApiResponse<>("배틀 예측 결과 불러오기에 성공했습니다.",
                battleService.calculateBattleResult(weatherId, myPokemonId, rivalPokemonId, myMoveId));
    }
}
