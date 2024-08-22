package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityResponse2

data class Ability(
    val id: String = "0",
    val title: String,
    val description: String,
)

fun AbilityResponse2.toData(): Ability =
    Ability(
        id = id,
        title = name,
        description = description,
    )
