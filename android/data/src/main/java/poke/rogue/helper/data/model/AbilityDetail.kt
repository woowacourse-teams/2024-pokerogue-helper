package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityDetailResponse

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
