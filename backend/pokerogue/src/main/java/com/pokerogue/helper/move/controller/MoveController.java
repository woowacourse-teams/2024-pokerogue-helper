package com.pokerogue.helper.move.controller;

import com.pokerogue.helper.move.dto.MoveDetailResponse;
import com.pokerogue.helper.move.dto.MoveResponse;
import com.pokerogue.helper.move.service.MoveService;
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
public class MoveController {

    private final MoveService moveService;

    @GetMapping("/api/v1/move/dex")
    public ApiResponse<List<MoveResponse>> moveList() {
        return new ApiResponse<>("기술 리스트 불러오기에 성공했습니다.", moveService.findMoves());
    }

    @GetMapping("/api/v1/moves")
    public ApiResponse<List<MoveResponse>> moveListByPokedexNumber(@RequestParam("pokedex-number") Integer pokedexNumber) {
        log.info(
                "---- URI : {}, Param : {}, ThreadName : {}",
                "/api/v1/moves?pokedex-number=",
                pokedexNumber,
                Thread.currentThread().getName()
        );

        return new ApiResponse<>("포켓몬의 기술 리스트 불러오기에 성공했습니다.", moveService.findMovesByPokemon(pokedexNumber));
    }

    @GetMapping("/api/v1/move/{id}")
    public ApiResponse<MoveResponse> moveDetailsInBattle(@PathVariable String id) {
        log.info(
                "---- URI : {}, Param : {}, ThreadName : {}",
                "/api/v1/move/{id}",
                id,
                Thread.currentThread().getName()
        );

        return new ApiResponse<>("기술 정보 불러오기에 성공했습니다.", moveService.findMoveInBattle(id));
    }

    @GetMapping("/api/v1/move/dex/{id}")
    public ApiResponse<MoveDetailResponse> moveDetails(@PathVariable String id) {
        log.info(
                "---- URI : {}, Param : {}, ThreadName : {}",
                "/api/v1/move/dex/{id}",
                id,
                Thread.currentThread().getName()
        );

        return new ApiResponse<>("기술 정보 불러오기에 성공했습니다.", moveService.findMove(id));
    }
}
