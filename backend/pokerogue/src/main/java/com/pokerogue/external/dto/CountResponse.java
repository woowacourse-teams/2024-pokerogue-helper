package com.pokerogue.external.dto;

// https://pokeapi.co/api/v2/{type, ability, pokemon}
// 해당 항목의 갯수를 받아오는 Dto
public record CountResponse(int count) {
}
