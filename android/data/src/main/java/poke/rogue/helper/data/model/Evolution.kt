package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.pokemon.EvolutionResponse

data class Evolution(
    val pokemonId: String,
    val pokemonName: String,
    val level: Int,
    val item: String?,
    val condition: String?,
)

fun EvolutionResponse.toData(): Evolution =
    Evolution(
        pokemonId = pokemonId,
        pokemonName = pokemonName,
        level = level,
        item = item,
        condition = condition,
    )

fun List<EvolutionResponse>.toData(): List<Evolution> =
    map(EvolutionResponse::toData)