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
                    "풀숲",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_grassy_fields_bg.png?w=200&tok=745c5b",
                ),
                Biome(
                    "2",
                    "높은 풀숲",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_tall_grass_bg.png?w=200&tok=b3497c",
                ),
                Biome(
                    "3",
                    "동굴",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_cave_bg.png?w=200&tok=905d8b",
                ),
            )
    }
}
