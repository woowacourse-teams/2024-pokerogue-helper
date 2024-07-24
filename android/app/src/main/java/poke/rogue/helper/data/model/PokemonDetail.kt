package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.pokemon.PokemonDetailResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class PokemonDetail(
    val pokemon: Pokemon,
    val stats: List<Stat>,
    val abilities: List<String>,
    val height: Float,
    val weight: Float,
)

fun PokemonDetailResponse.toData(id: Long): PokemonDetail {
    return PokemonDetail(
        Pokemon(
            id = id,
            dexNumber = dexNumber,
            name = name,
            imageUrl = imageUrl,
            types = types.map(PokemonTypeResponse::toData),
        ),
        stats =
            listOf(
                Stat("total", totalStats),
                Stat("hp", hp),
                Stat("attack", attack),
                Stat("defense", defense),
                Stat("specialAttack", specialAttack),
                Stat("specialDefense", specialDefense),
                Stat("speed", speed),
            ),
        abilities = abilityNames,
        height = height,
        weight = weight,
    )
}
