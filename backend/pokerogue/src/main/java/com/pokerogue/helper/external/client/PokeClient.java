package com.pokerogue.helper.external.client;

import com.pokerogue.helper.external.dto.CountResponse;
import com.pokerogue.helper.external.dto.ListResponse;
import com.pokerogue.helper.external.dto.ability.AbilityResponse;
import com.pokerogue.helper.external.dto.type.TypeResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface PokeClient {

    @GetExchange("/ability/?offset=0&limit={size}")
    ListResponse getAbilityList(@PathVariable String size);

    @GetExchange("/ability")
    CountResponse getAbilityListSize();

    @GetExchange("/ability/{id}")
    AbilityResponse getAbilityResponse(@PathVariable String id);

    @GetExchange("/type/?offset=0&limit={size}")
    ListResponse getTypeList(@PathVariable String size);

    @GetExchange("/type")
    CountResponse getTypeListSize();

    @GetExchange("/type/{id}")
    TypeResponse getTypeResponse(@PathVariable String id);
}
