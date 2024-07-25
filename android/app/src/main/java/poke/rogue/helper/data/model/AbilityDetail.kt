package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityDetailResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse

class AbilityDetail(
    val id: Long = 0,
    val name: String,
    val description: String,
    val pokemons: List<PokemonResponse>
)

fun AbilityDetailResponse.toData(): AbilityDetail =
    AbilityDetail(
        id = id,
        name = name,
        description = description,
        pokemons = pokemons,
    )
