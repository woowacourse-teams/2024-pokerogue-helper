package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityResponse2

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

fun AbilityResponse2.toData(): PokemonDetailAbility =
    PokemonDetailAbility(
        id = id,
        name = name,
        description = description,
        passive = passive,
        hidden = hidden,
    )

fun List<AbilityResponse2>.toData(): List<PokemonDetailAbility> =
    map(AbilityResponse2::toData)