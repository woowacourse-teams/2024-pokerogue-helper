package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.Type

class InMemoryPokemonListDataSource : PokemonListDataSource {
    override fun pokemons(): List<Pokemon> = POKEMONS

    companion object {
        private const val FORMAT_POKEMON_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other" +
                "/official-artwork/"

        private const val POSTFIX_PNG = ".png"

        private fun pokemonImageUrl(pokemonId: Long) = FORMAT_POKEMON_IMAGE_URL + pokemonId + POSTFIX_PNG

        private val POKEMONS: List<Pokemon> =
            listOf(
                Pokemon(
                    id = 1,
                    dexNumber = 1,
                    name = "이상해씨",
                    imageUrl = pokemonImageUrl(pokemonId = 1),
                    types = listOf(Type.of("grass"), Type.of("poison")),
                ),
                Pokemon(
                    id = 2,
                    dexNumber = 2,
                    name = "이상해풀",
                    imageUrl = pokemonImageUrl(pokemonId = 2),
                    types = listOf(Type.of("grass"), Type.of("poison")),
                ),
                Pokemon(
                    id = 3,
                    dexNumber = 3,
                    name = "이상해꽃",
                    imageUrl = pokemonImageUrl(pokemonId = 3),
                    types = listOf(Type.of("grass"), Type.of("poison")),
                ),
                Pokemon(
                    id = 4,
                    dexNumber = 4,
                    name = "파이리",
                    imageUrl = pokemonImageUrl(pokemonId = 4),
                    types = listOf(Type.of("fire")),
                ),
                Pokemon(
                    id = 5,
                    dexNumber = 5,
                    name = "리자드",
                    imageUrl = pokemonImageUrl(pokemonId = 5),
                    types = listOf(Type.of("fire")),
                ),
                Pokemon(
                    id = 6,
                    dexNumber = 6,
                    name = "리자몽",
                    imageUrl = pokemonImageUrl(pokemonId = 6),
                    types = listOf(Type.of("fire"), Type.of("flying")),
                ),
                Pokemon(
                    id = 7,
                    dexNumber = 7,
                    name = "꼬부기",
                    imageUrl = pokemonImageUrl(pokemonId = 7),
                    types = listOf(Type.of("water")),
                ),
                Pokemon(
                    id = 8,
                    dexNumber = 8,
                    name = "어니부기",
                    imageUrl = pokemonImageUrl(pokemonId = 8),
                    types = listOf(Type.of("water")),
                ),
                Pokemon(
                    id = 9,
                    dexNumber = 9,
                    name = "거북왕",
                    imageUrl = pokemonImageUrl(pokemonId = 9),
                    types = listOf(Type.of("water")),
                ),
                Pokemon(
                    id = 10,
                    dexNumber = 10,
                    name = "캐터피",
                    imageUrl = pokemonImageUrl(pokemonId = 10),
                    types = listOf(Type.of("bug")),
                ),
                Pokemon(
                    id = 11,
                    dexNumber = 11,
                    name = "단데기",
                    imageUrl = pokemonImageUrl(pokemonId = 11),
                    types = listOf(Type.of("bug")),
                ),
                Pokemon(
                    id = 12,
                    dexNumber = 12,
                    name = "버터플",
                    imageUrl = pokemonImageUrl(pokemonId = 12),
                    types = listOf(Type.of("bug"), Type.of("flying")),
                ),
                Pokemon(
                    id = 13,
                    dexNumber = 13,
                    name = "뿔충이",
                    imageUrl = pokemonImageUrl(pokemonId = 13),
                    types = listOf(Type.of("bug"), Type.of("poison")),
                ),
                Pokemon(
                    id = 14,
                    dexNumber = 14,
                    name = "딱충이",
                    imageUrl = pokemonImageUrl(pokemonId = 14),
                    types = listOf(Type.of("bug"), Type.of("poison")),
                ),
                Pokemon(
                    id = 15,
                    dexNumber = 15,
                    name = "독침붕",
                    imageUrl = pokemonImageUrl(pokemonId = 15),
                    types = listOf(Type.of("bug"), Type.of("poison")),
                ),
                Pokemon(
                    id = 16,
                    dexNumber = 16,
                    name = "구구",
                    imageUrl = pokemonImageUrl(pokemonId = 16),
                    types = listOf(Type.of("normal"), Type.of("flying")),
                ),
                Pokemon(
                    id = 17,
                    dexNumber = 17,
                    name = "피죤",
                    imageUrl = pokemonImageUrl(pokemonId = 17),
                    types = listOf(Type.of("normal"), Type.of("flying")),
                ),
                Pokemon(
                    id = 18,
                    dexNumber = 18,
                    name = "피죤투",
                    imageUrl = pokemonImageUrl(pokemonId = 18),
                    types = listOf(Type.of("normal"), Type.of("flying")),
                ),
                Pokemon(
                    id = 19,
                    dexNumber = 19,
                    name = "꼬렛",
                    imageUrl = pokemonImageUrl(pokemonId = 19),
                    types = listOf(Type.of("normal")),
                ),
                Pokemon(
                    id = 20,
                    dexNumber = 20,
                    name = "레트라",
                    imageUrl = pokemonImageUrl(pokemonId = 20),
                    types = listOf(Type.of("normal")),
                ),
                Pokemon(
                    id = 21,
                    dexNumber = 21,
                    name = "깨비참",
                    imageUrl = pokemonImageUrl(pokemonId = 21),
                    types = listOf(Type.of("normal"), Type.of("flying")),
                ),
                Pokemon(
                    id = 22,
                    dexNumber = 22,
                    name = "깨비드릴조",
                    imageUrl = pokemonImageUrl(pokemonId = 22),
                    types = listOf(Type.of("normal"), Type.of("flying")),
                ),
                Pokemon(
                    id = 23,
                    dexNumber = 23,
                    name = "아보",
                    imageUrl = pokemonImageUrl(pokemonId = 23),
                    types = listOf(Type.of("poison")),
                ),
                Pokemon(
                    id = 24,
                    dexNumber = 24,
                    name = "아보크",
                    imageUrl = pokemonImageUrl(pokemonId = 24),
                    types = listOf(Type.of("poison")),
                ),
                Pokemon(
                    id = 25,
                    dexNumber = 25,
                    name = "피카츄",
                    imageUrl = pokemonImageUrl(pokemonId = 25),
                    types = listOf(Type.of("electric")),
                ),
                Pokemon(
                    id = 26,
                    dexNumber = 26,
                    name = "라이츄",
                    imageUrl = pokemonImageUrl(pokemonId = 26),
                    types = listOf(Type.of("electric")),
                ),
                Pokemon(
                    id = 27,
                    dexNumber = 27,
                    name = "모래두지",
                    imageUrl = pokemonImageUrl(pokemonId = 27),
                    types = listOf(Type.of("ground")),
                ),
                Pokemon(
                    id = 28,
                    dexNumber = 28,
                    name = "고지",
                    imageUrl = pokemonImageUrl(pokemonId = 28),
                    types = listOf(Type.of("ground")),
                ),
                Pokemon(
                    id = 29,
                    dexNumber = 29,
                    name = "니드런",
                    imageUrl = pokemonImageUrl(pokemonId = 29),
                    types = listOf(Type.of("poison")),
                ),
                Pokemon(
                    id = 30,
                    dexNumber = 30,
                    name = "니드리나",
                    imageUrl = pokemonImageUrl(pokemonId = 30),
                    types = listOf(Type.of("poison")),
                ),
            )
    }
}
