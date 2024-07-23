package com.pokerogue.helper.pokemon.controller;

import com.pokerogue.helper.environ.controller.ControllerTest;
import com.pokerogue.helper.pokemon.service.PokemonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PokemonController.class)
class PokemonControllerTest extends ControllerTest {

    @MockBean
    private PokemonService pokemonService;

    @Test
    @DisplayName("유효하지 않은 형식의 포켓몬 id로 도감 상세 정보 요청 시 상태코드 400을 반환한다.")
    void pokedexDetailsWhenRequestWithInvalidName() throws Exception {
        String notExistingId = "ABC";

        mockMvc.perform(get("/api/v1/pokemon/{id}", notExistingId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
