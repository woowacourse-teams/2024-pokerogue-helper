package poke.rogue.helper.testing.data.repository

import poke.rogue.helper.data.model.Evolution
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonBiome
import poke.rogue.helper.data.model.PokemonCategory
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonDetailAbility.Companion.DUMMY_POKEMON_DETAIL_ABILTIES
import poke.rogue.helper.data.model.PokemonDetailSkills
import poke.rogue.helper.data.model.PokemonFilter
import poke.rogue.helper.data.model.PokemonGeneration
import poke.rogue.helper.data.model.PokemonSkill
import poke.rogue.helper.data.model.PokemonSort
import poke.rogue.helper.data.model.Stat
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.stringmatcher.has

class FakeDexRepository : DexRepository {
    override suspend fun warmUp() = Unit

    override suspend fun pokemons(): List<Pokemon> = POKEMONS

    override suspend fun filteredPokemons(
        name: String,
        sort: PokemonSort,
        filters: List<PokemonFilter>,
    ): List<Pokemon> {
        return if (name.isEmpty()) {
            pokemons()
        } else {
            pokemons().filter { it.name.has(name) }
        }.toFilteredPokemons(sort, filters)
    }

    override suspend fun pokemonDetail(id: String): PokemonDetail =
        PokemonDetail(
            pokemon = Pokemon.DUMMY,
            abilities = DUMMY_POKEMON_DETAIL_ABILTIES,
            stats = Stat.DUMMY_STATS,
            pokemonCategory = PokemonCategory.EMPTY,
            evolutions = Evolution.DUMMY_PICAKCHU_EVOLUTION,
            skills =
                PokemonDetailSkills(
                    selfLearn = PokemonSkill.FAKE_SELF_LEARN_SKILLS,
                    eggLearn = PokemonSkill.FAKE_EGG_LEARN_SKILLS,
                    tmLearn = PokemonSkill.FAKE_TM_LEARN_SKILLS,
                ),
            biomes = PokemonBiome.DUMMYS,
            height = 0.7,
            weight = 6.9,
        )

    override suspend fun pokemon(id: String): Pokemon {
        TODO("Not yet implemented")
    }

    private fun List<Pokemon>.toFilteredPokemons(
        sort: PokemonSort,
        filters: List<PokemonFilter>,
    ): List<Pokemon> {
        return this
            .filter { pokemon ->
                filters.all { filter ->
                    when (filter) {
                        is PokemonFilter.ByType -> pokemon.types.contains(filter.type)
                        is PokemonFilter.ByGeneration -> pokemon.generation == filter.generation
                    }
                }
            }
            .sortedWith(sort)
    }

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
                    backImageUrl = "",
                    types = listOf(Type.GRASS, Type.POISON),
                    generation = PokemonGeneration.ONE,
                    baseStat = 318,
                    hp = 45,
                    attack = 49,
                    defense = 49,
                    specialAttack = 65,
                    specialDefense = 65,
                    speed = 45,
                ),
                Pokemon(
                    id = "2",
                    dexNumber = 2,
                    name = "이상해풀",
                    imageUrl = pokemonImageUrl(pokemonId = 2),
                    backImageUrl = "",
                    types = listOf(Type.GRASS, Type.POISON),
                    generation = PokemonGeneration.ONE,
                    baseStat = 405,
                    hp = 60,
                    attack = 62,
                    defense = 63,
                    specialAttack = 80,
                    specialDefense = 80,
                    speed = 60,
                ),
                Pokemon(
                    id = "3",
                    dexNumber = 3,
                    name = "이상해꽃",
                    imageUrl = pokemonImageUrl(pokemonId = 3),
                    backImageUrl = "",
                    types = listOf(Type.GRASS, Type.POISON),
                    generation = PokemonGeneration.ONE,
                    baseStat = 525,
                    hp = 80,
                    attack = 82,
                    defense = 83,
                    specialAttack = 100,
                    specialDefense = 100,
                    speed = 80,
                ),
                Pokemon(
                    id = "4",
                    dexNumber = 4,
                    name = "파이리",
                    imageUrl = pokemonImageUrl(pokemonId = 4),
                    backImageUrl = "",
                    types = listOf(Type.FIRE),
                    generation = PokemonGeneration.ONE,
                    baseStat = 309,
                    hp = 39,
                    attack = 52,
                    defense = 43,
                    specialAttack = 60,
                    specialDefense = 50,
                    speed = 65,
                ),
                Pokemon(
                    id = "5",
                    dexNumber = 5,
                    name = "리자드",
                    imageUrl = pokemonImageUrl(pokemonId = 5),
                    backImageUrl = "",
                    types = listOf(Type.FIRE),
                    generation = PokemonGeneration.ONE,
                    baseStat = 405,
                    hp = 58,
                    attack = 64,
                    defense = 58,
                    specialAttack = 80,
                    specialDefense = 65,
                    speed = 80,
                ),
                Pokemon(
                    id = "6",
                    dexNumber = 6,
                    name = "리자몽",
                    imageUrl = pokemonImageUrl(pokemonId = 6),
                    backImageUrl = "",
                    types = listOf(Type.FIRE, Type.FLYING),
                    generation = PokemonGeneration.ONE,
                    baseStat = 534,
                    hp = 78,
                    attack = 84,
                    defense = 78,
                    specialAttack = 109,
                    specialDefense = 85,
                    speed = 100,
                ),
                Pokemon(
                    id = "7",
                    dexNumber = 7,
                    name = "꼬부기",
                    imageUrl = pokemonImageUrl(pokemonId = 7),
                    backImageUrl = "",
                    types = listOf(Type.WATER),
                    generation = PokemonGeneration.ONE,
                    baseStat = 314,
                    hp = 44,
                    attack = 48,
                    defense = 65,
                    specialAttack = 50,
                    specialDefense = 64,
                    speed = 43,
                ),
                Pokemon(
                    id = "8",
                    dexNumber = 8,
                    name = "어니부기",
                    imageUrl = pokemonImageUrl(pokemonId = 8),
                    backImageUrl = "",
                    types = listOf(Type.WATER),
                    generation = PokemonGeneration.ONE,
                    baseStat = 405,
                    hp = 59,
                    attack = 63,
                    defense = 80,
                    specialAttack = 65,
                    specialDefense = 80,
                    speed = 58,
                ),
                Pokemon(
                    id = "9",
                    dexNumber = 9,
                    name = "거북왕",
                    imageUrl = pokemonImageUrl(pokemonId = 9),
                    backImageUrl = "",
                    types = listOf(Type.WATER),
                    generation = PokemonGeneration.ONE,
                    baseStat = 530,
                    hp = 79,
                    attack = 83,
                    defense = 100,
                    specialAttack = 85,
                    specialDefense = 105,
                    speed = 78,
                ),
                Pokemon(
                    id = "373",
                    dexNumber = 373,
                    name = "보만다",
                    imageUrl = pokemonImageUrl(pokemonId = 373),
                    backImageUrl = "",
                    types = listOf(Type.FLYING, Type.DRAGON),
                    generation = PokemonGeneration.THREE,
                    baseStat = 600,
                    hp = 95,
                    attack = 135,
                    defense = 80,
                    specialAttack = 110,
                    specialDefense = 80,
                    speed = 100,
                ),
            )
    }
}
