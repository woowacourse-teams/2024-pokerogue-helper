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
import poke.rogue.helper.remote.dto.response.biomes.BiomesResponse

data class Biome2(
    val id: String,
    val name: String,
    val image: String,
    val pokemonType: List<String>,
    val gymLeaderType: List<String>,
)

fun BiomesResponse.toData(): Biome2 =
    Biome2(
        id = id,
        name = name,
        image = image,
        pokemonType = pokemonType,
        gymLeaderType = gymLeaderType,
    )
