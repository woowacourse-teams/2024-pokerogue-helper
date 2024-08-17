package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityResponse

data class Ability(
    val id: String = "0",
    val title: String,
    val description: String,
)

// TODO: 서버에서 String 으로 주면 id.toString 제거해도 된다 
fun AbilityResponse.toData(): Ability =
    Ability(
        id = id.toString(),
        title = title,
        description = description,
    )
