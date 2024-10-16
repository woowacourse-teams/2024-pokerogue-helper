package com.pokerogue.helper.biome.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pokerogue.environment.controller.ControllerTest;
import com.pokerogue.helper.biome.service.BiomeService;
import com.pokerogue.helper.global.constant.SortingCriteria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(value = BiomeController.class)
class BiomeControllerTest extends ControllerTest {

    @MockBean
    private BiomeService biomeService;

    @Test
    @DisplayName("바이옴 포켓몬 정렬 기준 쿼리 스트링을 바인딩한다.")
    void bindBiomePokemonSortingCriteriaRequestParameter() throws Exception {
        mockMvc.perform(get("/api/v1/biome/test_id")
                        .content(MediaType.APPLICATION_JSON_VALUE)
                        .param("boss", "asc").param("wild", "desc"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(biomeService).findBiome("test_id", SortingCriteria.ASCENDING, SortingCriteria.DESCENDING);
    }

    @Test
    @DisplayName("바이옴 포켓몬 정렬 기준 쿼리 스트링을 설정하지 않았다면 기본값으로 바인딩한다.")
    void bindDefaultBiomePokemonSortingCriteriaRequestParameter() throws Exception {
        mockMvc.perform(get("/api/v1/biome/test_id")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        verify(biomeService).findBiome("test_id", SortingCriteria.DESCENDING, SortingCriteria.ASCENDING);
    }


    @Test
    @DisplayName("잘못된 값의 바이옴 포켓몬 정렬 기준 쿼리 스트링으로 요청하면 예외 응답을 반환한다.")
    void handlesInvalidSortingCriteriaRequestParameter() throws Exception {
        mockMvc.perform(get("/api/v1/biome/test_id")
                        .content(MediaType.APPLICATION_JSON_VALUE)
                        .param("boss", "ascc").param("wild", "desc"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Failed to convert 'boss' with value: 'ascc'"));
    }
}
