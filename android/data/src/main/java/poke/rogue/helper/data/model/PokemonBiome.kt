package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.biom.PokemonBiomeResponse

data class PokemonBiome(
    val id: String,
    val name: String,
    val imageUrl: String,
    val pokemonType: List<Type> = emptyList(),
) {
    companion object {
        val DUMMYS: List<PokemonBiome> =
            listOf(
                PokemonBiome(
                    id = "1",
                    name = "평야",
                    imageUrl = "https://pokeroguedex.com/biomes/plains.png",
                    pokemonType = listOf(Type.GRASS, Type.BUG),
                ),
                PokemonBiome(
                    id = "2",
                    name = "높은 풀숲",
                    imageUrl = "https://pokeroguedex.com/biomes/tall-grass.png",
                    pokemonType = listOf(Type.GRASS, Type.BUG, Type.FLYING),
                ),
                PokemonBiome(
                    id = "3",
                    name = "동굴",
                    imageUrl = "https://pokeroguedex.com/biomes/cave.png",
                    pokemonType = listOf(Type.GROUND, Type.ROCK),
                ),
            )
    }
}

fun PokemonBiomeResponse.toData(): PokemonBiome =
    PokemonBiome(
        id = id,
        name = name,
        imageUrl = image,
    )

fun List<PokemonBiomeResponse>.toData(): List<PokemonBiome> = map(PokemonBiomeResponse::toData)
