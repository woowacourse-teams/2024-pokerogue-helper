package com.pokerogue.helper.item.controller;

import com.pokerogue.helper.item.service.ItemResponse;
import com.pokerogue.helper.item.service.ItemService;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/api/v1/items")
    public ApiResponse<List<ItemResponse>> pokemonList() {
        return new ApiResponse<>("아이템 리스트 불러오기에 성공했습니다.", itemService.findAll());
    }

}
