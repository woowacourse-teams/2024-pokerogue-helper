package poke.rogue.helper.data.model

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
