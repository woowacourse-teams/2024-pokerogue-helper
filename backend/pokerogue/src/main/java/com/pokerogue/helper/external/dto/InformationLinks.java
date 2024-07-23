package com.pokerogue.helper.external.dto;

import java.util.List;

// https://pokeapi.co/api/v2/{type, ability, pokemon}/?offset=0&limit={count}
// 해당 항목의 전체 리스트를 받아오는 Dto
public record InformationLinks(List<InformationLink> results) {
}
