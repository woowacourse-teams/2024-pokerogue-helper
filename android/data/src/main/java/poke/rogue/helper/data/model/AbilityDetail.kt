package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityDetailResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityDetailResponse2
import poke.rogue.helper.remote.dto.response.ability.AbilityPokemonResponse

class AbilityDetail(
    val title: String,
    val description: String,
    val pokemons: List<Pokemon>,
)

fun AbilityDetailResponse.toData(): AbilityDetail =
    AbilityDetail(
        title = title,
        description = description,
        pokemons = pokemons.toData(),
    )

fun AbilityDetailResponse2.toData(): AbilityDetail =
    AbilityDetail(
        title = name,
        description = description,
        pokemons = pokemons.map(AbilityPokemonResponse::toData),
    )
