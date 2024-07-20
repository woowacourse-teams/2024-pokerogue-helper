package com.pokerogue.helper.external.dto;

import java.util.List;

// https://pokeapi.co/api/v2/{type, ability, pokemon}/?offset=0&limit={count}
public record ListResponse(List<NameAndUrl> results) {
}
