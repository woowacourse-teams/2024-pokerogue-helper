package com.pokerogue.helper.pokemon.controller;

import com.pokerogue.external.pokemon.service.DataSettingService;
import com.pokerogue.helper.pokemon.dto.PokedexResponse;
import com.pokerogue.helper.pokemon.dto.PokemonResponse;
import com.pokerogue.helper.pokemon.service.PokemonService;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;
    private final DataSettingService dataSettingService;

    @GetMapping("/api/v1/pokemons")
    public ApiResponse<List<PokemonResponse>> pokemonList() {
        return new ApiResponse<>("포켓몬 리스트 불러오기에 성공했습니다.", pokemonService.findPokemons());
    }

    @GetMapping("/api/v1/pokemon/{id}")
    public ApiResponse<PokedexResponse> pokedexDetails(@PathVariable("id") Long id) {
        log.info("---- URI : {}, Param : {}", "/api/v1/pokemon/{id}", id);

        return new ApiResponse<>("포켓몬 정보 불러오기에 성공했습니다.", pokemonService.findPokedexDetails(id));
    }

    @PostMapping("/api/v1/setting")
    public ApiResponse<Void> savePokemonData() {
        log.info("---- 포켓몬 데이터 세팅 시작");
        dataSettingService.setData();

        return new ApiResponse<>("포켓몬 데이터 저장에 성공했습니다.", null);
    }

    @PostMapping("/api/v1/pre")
    public ApiResponse<Void> savePokemonDataSetting() {
        log.info("---- 포켓몬 전체 데이터 Pre 테스트 시작");
        dataSettingService.setData();

        return new ApiResponse<>("포켓몬 전체 데이터 Pre 테스트에 성공했습니다.", null);
    }

    @PostMapping("/api/v1/batch")
    public ApiResponse<Void> savePokemonDataBatch() {
        log.info("---- 포켓몬 전체 데이터 Batch 테스트 시작");
        dataSettingService.setDataBatch();

        return new ApiResponse<>("포켓몬 전체 데이터 Batch 테스트에 성공했습니다.", null);
    }

    @PostMapping("/api/v1/pre/type")
    public ApiResponse<Void> savePokemonDataTypeSetting() {
        log.info("---- 포켓몬 타입 데이터 Pre 테스트 시작");
        dataSettingService.setDataTestTypePre();

        return new ApiResponse<>("포켓몬 타입 데이터 Pre 테스트에 성공했습니다.", null);
    }

    @PostMapping("/api/v1/pre/pokemon")
    public ApiResponse<Void> savePokemonDataPokemonSetting() {
        log.info("---- 포켓몬 데이터 Pre 테스트 시작");
        dataSettingService.setDataTestPokemonPre();

        return new ApiResponse<>("포켓몬 데이터 Pre 테스트에 성공했습니다.", null);
    }

    @PostMapping("/api/v1/pre/ability")
    public ApiResponse<Void> savePokemonDataAbilitySetting() {
        log.info("---- 포켓몬 특성 데이터 Pre 테스트 시작");
        dataSettingService.setDataTestAbilityPre();

        return new ApiResponse<>("포켓몬 특성 데이터 Pre 테스트에 성공했습니다.", null);
    }

    @PostMapping("/api/v1/batch/type")
    public ApiResponse<Void> savePokemonDataType() {
        log.info("---- 포켓몬 타입 데이터 Batch 테스트 시작");
        dataSettingService.setDataTestTypeBatch();

        return new ApiResponse<>("포켓몬 타입 데이터 Batch 테스트에 성공했습니다.", null);
    }

    @PostMapping("/api/v1/batch/pokemon")
    public ApiResponse<Void> savePokemonDataPokemon() {
        log.info("---- 포켓몬 데이터 Batch 테스트 시작");
        dataSettingService.setDataTestPokemonBatch();

        return new ApiResponse<>("포켓몬 데이터 Batch 테스트에 성공했습니다.", null);
    }

    @PostMapping("/api/v1/batch/ability")
    public ApiResponse<Void> savePokemonDataAbility() {
        log.info("---- 포켓몬 특성 데이터 Batch 테스트 시작");
        dataSettingService.setDataTestAbilityBatch();

        return new ApiResponse<>("포켓몬 특성 데이터 Batch 테스트에 성공했습니다.", null);
    }
}
