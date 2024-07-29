package poke.rogue.helper.testing.fixture

import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

/**
 * Test fixture for [PokemonTypeResponse].
 * @param typeName 포켓몬 타입의 이름(영어 소문자). 예) "fire", "water"
 * @return [PokemonTypeResponse]
 *
 * 타입은 typeName, 로고는 "logo " + typeName 이 된다
 */
fun pokemonTypeResponse(typeName: String) =
    PokemonTypeResponse(
        pokemonTypeName = typeName,
        pokemonTypeLogo = "logo $typeName",
    )

/**
 * Test fixture for [List]<[PokemonTypeResponse]>.
 * @param typeNames 포켓몬 타입의 이름 목록(영어 소문자). 예) "fire", "water"
 * @return [List]<[PokemonTypeResponse]>
 *
 * sample
 * ```kotlin
 * val typeResponses = pokemonTypeResponses("fire", "water")
 *```
 */
fun pokemonTypeResponses(vararg typeNames: String) = typeNames.map(::pokemonTypeResponse)
