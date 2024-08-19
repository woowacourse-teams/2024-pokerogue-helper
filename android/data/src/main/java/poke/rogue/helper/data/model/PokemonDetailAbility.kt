package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityResponse

data class PokemonDetailAbility(
    val id: String,
    val name: String,
    val description: String,
    val passive: Boolean,
    val hidden: Boolean,
)

fun AbilityResponse.toNewData(): PokemonDetailAbility =
    PokemonDetailAbility(
        id = id.toString(),
        name = title,
        description = description,
        passive = false,
        hidden = false,
    )
