package com.pokerogue.helper.type.controller;

import com.pokerogue.helper.external.client.PokeClient;
import com.pokerogue.helper.external.dto.CountResponse;
import com.pokerogue.helper.external.dto.ListResponse;
import com.pokerogue.helper.external.dto.NameAndUrl;
import com.pokerogue.helper.external.dto.type.TypeResponse;
import com.pokerogue.helper.type.domain.PokemonType;
import com.pokerogue.helper.util.Saver;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PokemonTypeController {

    private final Saver saver;
    private final PokeClient pokeClient;

    @GetMapping("/api/v1/update/type")
    public ApiResponse<List<PokemonType>> saveTypeList() {
        CountResponse typeListSize = pokeClient.getTypeListSize();
        ListResponse typeList = pokeClient.getTypeList(String.valueOf(typeListSize.count()));
        List<TypeResponse> typeResponses = new ArrayList<>();
        for (NameAndUrl nameAndUrl : typeList.results()) {
            String[] split = nameAndUrl.url().split("/");
            TypeResponse typeResponse = pokeClient.getTypeResponse(split[split.length - 1]);
            typeResponses.add(typeResponse);
        }
        List<PokemonType> savedPokemonTypes = saver.savePokemonTypeList(typeResponses);
        return new ApiResponse<>("타입 정보 업데이트", savedPokemonTypes);
    }
}
