package poke.rogue.helper.testing.data.repository

import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonCategory
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonDetailAbility
import poke.rogue.helper.data.model.PokemonDetailSkills
import poke.rogue.helper.data.model.PokemonSkill
import poke.rogue.helper.data.model.Stat
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.repository.DexRepository

class FakeDexRepository : DexRepository {
    override suspend fun pokemons(): List<Pokemon> = POKEMONS

    override suspend fun pokemons(query: String): List<Pokemon> =
        POKEMONS.filter { pokemon ->
            pokemon.name.contains(query, ignoreCase = true)
        }

    override suspend fun pokemonDetail(id: String): PokemonDetail = DUMMY_POKEMON_DETAIL

    companion object {
        private const val FORMAT_POKEMON_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other" +
                "/official-artwork/"

        private const val POSTFIX_PNG = ".png"

        private fun pokemonImageUrl(pokemonId: Long) = FORMAT_POKEMON_IMAGE_URL + pokemonId + POSTFIX_PNG

        val POKEMONS: List<Pokemon> =
            listOf(
                Pokemon(
                    id = "1",
                    dexNumber = 1,
                    name = "이상해씨",
                    imageUrl = pokemonImageUrl(pokemonId = 1),
                    types = listOf(Type.GRASS, Type.POISON),
                ),
                Pokemon(
                    id = "2",
                    dexNumber = 2,
                    name = "이상해풀",
                    imageUrl = pokemonImageUrl(pokemonId = 2),
                    types = listOf(Type.GRASS, Type.POISON),
                ),
                Pokemon(
                    id = "3",
                    dexNumber = 3,
                    name = "이상해꽃",
                    imageUrl = pokemonImageUrl(pokemonId = 3),
                    types = listOf(Type.GRASS, Type.POISON),
                ),
                Pokemon(
                    id = "4",
                    dexNumber = 4,
                    name = "파이리",
                    imageUrl = pokemonImageUrl(pokemonId = 4),
                    types = listOf(Type.FIRE),
                ),
                Pokemon(
                    id = "5",
                    dexNumber = 5,
                    name = "리자드",
                    imageUrl = pokemonImageUrl(pokemonId = 5),
                    types = listOf(Type.FIRE),
                ),
                Pokemon(
                    id = "6",
                    dexNumber = 6,
                    name = "리자몽",
                    imageUrl = pokemonImageUrl(pokemonId = 6),
                    types = listOf(Type.FIRE, Type.FLYING),
                ),
                Pokemon(
                    id = "7",
                    dexNumber = 7,
                    name = "꼬부기",
                    imageUrl = pokemonImageUrl(pokemonId = 7),
                    types = listOf(Type.WATER),
                ),
                Pokemon(
                    id = "8",
                    dexNumber = 8,
                    name = "어니부기",
                    imageUrl = pokemonImageUrl(pokemonId = 8),
                    types = listOf(Type.WATER),
                ),
                Pokemon(
                    id = "9",
                    dexNumber = 9,
                    name = "거북왕",
                    imageUrl = pokemonImageUrl(pokemonId = 9),
                    types = listOf(Type.WATER),
                ),
                Pokemon(
                    id = "10",
                    dexNumber = 10,
                    name = "캐터피",
                    imageUrl = pokemonImageUrl(pokemonId = 10),
                    types = listOf(Type.BUG),
                ),
                Pokemon(
                    id = "11",
                    dexNumber = 11,
                    name = "단데기",
                    imageUrl = pokemonImageUrl(pokemonId = 11),
                    types = listOf(Type.BUG),
                ),
                Pokemon(
                    id = "12",
                    dexNumber = 12,
                    name = "버터플",
                    imageUrl = pokemonImageUrl(pokemonId = 12),
                    types = listOf(Type.BUG, Type.FLYING),
                ),
                Pokemon(
                    id = "13",
                    dexNumber = 13,
                    name = "뿔충이",
                    imageUrl = pokemonImageUrl(pokemonId = 13),
                    types = listOf(Type.BUG, Type.POISON),
                ),
                Pokemon(
                    id = "14",
                    dexNumber = 14,
                    name = "딱충이",
                    imageUrl = pokemonImageUrl(pokemonId = 14),
                    types = listOf(Type.BUG, Type.POISON),
                ),
                Pokemon(
                    id = "15",
                    dexNumber = 15,
                    name = "독침붕",
                    imageUrl = pokemonImageUrl(pokemonId = 15),
                    types = listOf(Type.BUG, Type.POISON),
                ),
                Pokemon(
                    id = "16",
                    dexNumber = 16,
                    name = "구구",
                    imageUrl = pokemonImageUrl(pokemonId = 16),
                    types = listOf(Type.NORMAL, Type.FLYING),
                ),
                Pokemon(
                    id = "17",
                    dexNumber = 17,
                    name = "피죤",
                    imageUrl = pokemonImageUrl(pokemonId = 17),
                    types = listOf(Type.NORMAL, Type.FLYING),
                ),
                Pokemon(
                    id = "18",
                    dexNumber = 18,
                    name = "피죤투",
                    imageUrl = pokemonImageUrl(pokemonId = 18),
                    types = listOf(Type.NORMAL, Type.FLYING),
                ),
                Pokemon(
                    id = "19",
                    dexNumber = 19,
                    name = "꼬렛",
                    imageUrl = pokemonImageUrl(pokemonId = 19),
                    types = listOf(Type.NORMAL),
                ),
                Pokemon(
                    id = "20",
                    dexNumber = 20,
                    name = "레트라",
                    imageUrl = pokemonImageUrl(pokemonId = 20),
                    types = listOf(Type.NORMAL),
                ),
                Pokemon(
                    id = "21",
                    dexNumber = 21,
                    name = "깨비참",
                    imageUrl = pokemonImageUrl(pokemonId = 21),
                    types = listOf(Type.NORMAL, Type.FLYING),
                ),
                Pokemon(
                    id = "22",
                    dexNumber = 22,
                    name = "깨비드릴조",
                    imageUrl = pokemonImageUrl(pokemonId = 22),
                    types = listOf(Type.NORMAL, Type.FLYING),
                ),
                Pokemon(
                    id = "23",
                    dexNumber = 23,
                    name = "아보",
                    imageUrl = pokemonImageUrl(pokemonId = 23),
                    types = listOf(Type.POISON),
                ),
                Pokemon(
                    id = "24",
                    dexNumber = 24,
                    name = "아보크",
                    imageUrl = pokemonImageUrl(pokemonId = 24),
                    types = listOf(Type.POISON),
                ),
                Pokemon(
                    id = "25",
                    dexNumber = 25,
                    name = "피카츄",
                    imageUrl = pokemonImageUrl(pokemonId = 25),
                    types = listOf(Type.ELECTRIC),
                ),
                Pokemon(
                    id = "26",
                    dexNumber = 26,
                    name = "라이츄",
                    imageUrl = pokemonImageUrl(pokemonId = 26),
                    types = listOf(Type.ELECTRIC),
                ),
                Pokemon(
                    id = "27",
                    dexNumber = 27,
                    name = "모래두지",
                    imageUrl = pokemonImageUrl(pokemonId = 27),
                    types = listOf(Type.GROUND),
                ),
                Pokemon(
                    id = "28",
                    dexNumber = 28,
                    name = "고지",
                    imageUrl = pokemonImageUrl(pokemonId = 28),
                    types = listOf(Type.GROUND),
                ),
                Pokemon(
                    id = "29",
                    dexNumber = 29,
                    name = "니드런",
                    imageUrl = pokemonImageUrl(pokemonId = 29),
                    types = listOf(Type.POISON),
                ),
                Pokemon(
                    id = "30",
                    dexNumber = 30,
                    name = "니드리나",
                    imageUrl = pokemonImageUrl(pokemonId = 30),
                    types = listOf(Type.POISON),
                ),
            )

        val DUMMY_POKEMON_DETAIL =
            PokemonDetail(
                pokemon = Pokemon.DUMMY,
                abilities =
                    listOf(
                        PokemonDetailAbility("450", "심록", description = "HP가 줄었을 때 풀타입 기술의 위력이 올라간다.", false, false),
                        PokemonDetailAbility("419", "엽록소", description = "날씨가 맑을 때 스피드가 올라간다.", false, false),
                    ),
                stats =
                    listOf(
                        Stat("hp", 45),
                        Stat("attack", 49),
                        Stat("defense", 49),
                        Stat("specialAttack", 65),
                        Stat("specialDefense", 65),
                        Stat("speed", 45),
                        Stat("total", 318),
                    ),
                pokemonCategory = PokemonCategory.EMPTY,
                evolutions = emptyList(),
                skills =
                    PokemonDetailSkills(
                        selfLearn = PokemonSkill.FAKE_SELF_LEARN_SKILLS,
                        tmLearn = PokemonSkill.FAKE_TM_LEARN_SKILLS,
                        eggLearn = PokemonSkill.FAKE_EGG_LEARN_SKILLS,
                    ),
                biomes = Biome.DUMMYS,
                height = 0.7,
                weight = 6.9,
            )
    }
}
