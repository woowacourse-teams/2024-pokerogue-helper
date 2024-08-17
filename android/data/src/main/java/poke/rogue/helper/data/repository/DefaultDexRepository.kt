package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemoteDexDataSource
import poke.rogue.helper.data.model.NewPokemon
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.stringmatcher.has

class DefaultDexRepository(
    private val dexDataSource: RemoteDexDataSource,
) : DexRepository {
    private var cachedPokemons: List<NewPokemon> = emptyList()
    private var cachedPokemonDetails: MutableMap<Long, PokemonDetail> = mutableMapOf()

    override suspend fun pokemons(): List<NewPokemon> {
        if (cachedPokemons.isEmpty()) {
            cachedPokemons = dexDataSource.pokemons().toNew()
        }
        return cachedPokemons
    }

    override suspend fun pokemons(query: String): List<NewPokemon> {
        if (query.isBlank()) {
            return pokemons()
        }
        return pokemons().filter { it.name.has(query) }
    }

    override suspend fun pokemonDetail(id: Long): PokemonDetail {
        val cached = cachedPokemonDetails[id]
        if (cached != null) {
            return cached
        }
        return dexDataSource.pokemon(id).also {
            cachedPokemonDetails[id] = it
        }
    }

    companion object {
        private var instance: DexRepository? = null

        fun instance(): DexRepository {
            return instance
                ?: DefaultDexRepository(RemoteDexDataSource.instance()).also {
                    instance = it
                }
        }
    }
}

// TODO: 리턴을 NewPokemon 으로 변경
fun Pokemon.toNew(): NewPokemon =
    NewPokemon(
        id = id.toString(),
        dexNumber = dexNumber,
        name = name,
        imageUrl = imageUrl,
        types = types,
    )

fun List<Pokemon>.toNew(): List<NewPokemon> = map(Pokemon::toNew)

