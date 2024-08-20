package poke.rogue.helper.data.repository

import android.content.Context
import poke.rogue.helper.data.datasource.LocalDexDataSource
import poke.rogue.helper.data.datasource.RemoteDexDataSource
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonFilter
import poke.rogue.helper.data.model.PokemonGeneration
import poke.rogue.helper.data.model.PokemonSort
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.stringmatcher.has

class DefaultDexRepository(
    private val remotePokemonDataSource: RemoteDexDataSource,
    private val localPokemonDataSource: LocalDexDataSource,
) : DexRepository {
    private var cachedPokemons: List<Pokemon> = emptyList()
    private var cachedPokemonDetails: MutableMap<String, PokemonDetail> = mutableMapOf()

    override suspend fun warmUp() {
        if (localPokemonDataSource.pokemons().isEmpty()) {
            localPokemonDataSource.savePokemons(remotePokemonDataSource.pokemons2())
        }
        cachedPokemons = localPokemonDataSource.pokemons()
    }

    override suspend fun pokemons(): List<Pokemon> {
        return POKEMONS
        if (cachedPokemons.isEmpty()) {
            warmUp()
        }
        return cachedPokemons
    }

    override suspend fun filteredPokemons(
        name: String,
        sort: PokemonSort,
        filters: List<PokemonFilter>,
    ): List<Pokemon> {
        return if (name.isBlank()) {
            pokemons()
        } else {
            pokemons().filter { it.name.has(name) }
        }.toFilteredPokemons(sort, filters)
    }

    override suspend fun pokemonDetail(id: String): PokemonDetail {
        val cached = cachedPokemonDetails[id]
        if (cached != null) {
            return cached
        }
        return remotePokemonDataSource.pokemon(id).also {
            cachedPokemonDetails[id] = it
        }
    }

    private fun List<Pokemon>.toFilteredPokemons(
        sort: PokemonSort,
        pokemonFilters: List<PokemonFilter>,
    ): List<Pokemon> {
        return this
            .filter { pokemon ->
                pokemonFilters.all { pokemonFilter ->
                    when (pokemonFilter) {
                        is PokemonFilter.ByType -> pokemon.types.contains(pokemonFilter.type)
                        is PokemonFilter.ByGeneration -> pokemon.generation == pokemonFilter.generation
                    }
                }
            }
            .sortedWith(sort)
    }

    companion object {
        private var instance: DexRepository? = null

        fun init(context: Context) {
            instance =
                DefaultDexRepository(
                    RemoteDexDataSource.instance(),
                    LocalDexDataSource.instance(context),
                )
        }

        fun instance(): DexRepository {
            return requireNotNull(instance) {
                "DexRepository is not initialized"
            }
        }
    }
}


private const val FORMAT_POKEMON_IMAGE_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other" +
            "/official-artwork/"

private const val POSTFIX_PNG = ".png"

private fun pokemonImageUrl(pokemonId: Long) =
    FORMAT_POKEMON_IMAGE_URL + pokemonId + POSTFIX_PNG

val POKEMONS: List<Pokemon> =
    listOf(
        Pokemon(
            id = "1",
            dexNumber = 1,
            name = "이상해씨",
            imageUrl = pokemonImageUrl(pokemonId = 1),
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
