package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityResponse

data class Ability(
    val id: Long = 0,
    val name: String,
    val description: String,
)

fun AbilityResponse.toData(): Ability =
    Ability(
        id = id,
        name = name,
        description = description,
    )
