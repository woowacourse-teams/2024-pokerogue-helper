package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityResponse

data class NewAbility(
    val id: String,
    val name: String,
    val description: String,
    val passive: Boolean,
    val hidden: Boolean,
)

fun AbilityResponse.toNewData(): NewAbility =
    NewAbility(
        id = id.toString(),
        name = title,
        description = description,
        passive = false,
        hidden = false,
    )
