package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.biom.PokemonBiomeResponse

data class PokemonBiome(
    val id: String,
    val name: String,
    val imageUrl: String,
) {
    companion object {
        val DUMMYS: List<PokemonBiome> =
            listOf(
                PokemonBiome(
                    "1",
                    "평야",
                    "https://pokeroguedex.com/biomes/plains.png",
                ),
                PokemonBiome(
                    "2",
                    "높은 풀숲",
                    "https://pokeroguedex.com/biomes/tall-grass.png",
                ),
                PokemonBiome(
                    "3",
                    "동굴",
                    "https://pokeroguedex.com/biomes/cave.png",
                ),
            )
    }
}


fun PokemonBiomeResponse.toData(): PokemonBiome = PokemonBiome(
    id = id,
    name = name,
    imageUrl = image,
)

fun List<PokemonBiomeResponse>.toData(): List<PokemonBiome> = map(PokemonBiomeResponse::toData)
