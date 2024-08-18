package com.pokerogue.helper.pokemon2;


import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Pokemon2Controller {

    private final Pokemon2Service pokemon2Service;

    @GetMapping("/api/v1/pokemons2")
    public ApiResponse<List<Map<Object, Object>>> findAll() {
        return new ApiResponse<>("포켓몬 리스트 불러오기에 성공했습니다.", pokemon2Service.findAll());
    }

    @GetMapping("/api/v1/pokemon2/{id}/all")
    public ApiResponse<PokemonDetailResponse> findAll(@PathVariable("id") String id) {
        return new ApiResponse<>("포켓몬 정보 불러오기에 성공했습니다.", pokemon2Service.findById(id));
    }
}
