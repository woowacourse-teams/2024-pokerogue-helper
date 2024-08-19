package poke.rogue.helper.data.model

data class Biome(
    val id: String,
    val name: String,
    val imageUrl: String,
) {
    companion object {
        val DUMMYS: List<Biome> =
            listOf(
                Biome(
                    "1",
                    "평야",
                    "https://pokeroguedex.com/biomes/plains.png",
                ),
                Biome(
                    "2",
                    "높은 풀숲",
                    "https://pokeroguedex.com/biomes/tall-grass.png",
                ),
                Biome(
                    "3",
                    "동굴",
                    "https://pokeroguedex.com/biomes/cave.png",
                ),
            )
    }
}
