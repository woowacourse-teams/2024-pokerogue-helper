package poke.rogue.helper.data.model.biome

import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.dto.response.biomes.BiomePokemonResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class BiomePokemon(
    val name: String,
    val image: String,
    val types: List<Type>
)

fun BiomePokemonResponse.toData(): BiomePokemon =
    BiomePokemon(
        name = name,
        image = image,
        types = types.toData()
    )

fun List<BiomePokemonResponse>.toData(): List<BiomePokemon> =
    map(BiomePokemonResponse::toData)
