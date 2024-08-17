package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemoteDexDataSource
import poke.rogue.helper.data.model.NewPokemon
import poke.rogue.helper.data.model.NewPokemonDetail
import poke.rogue.helper.stringmatcher.has

class DefaultDexRepository(
    private val dexDataSource: RemoteDexDataSource,
) : DexRepository {
    private var cachedPokemons: List<NewPokemon> = emptyList()
    private var cachedPokemonDetails: MutableMap<String, NewPokemonDetail> = mutableMapOf()

    override suspend fun pokemons(): List<NewPokemon> {
        if (cachedPokemons.isEmpty()) {
            cachedPokemons = dexDataSource.pokemons()
        }
        return cachedPokemons
    }

    override suspend fun pokemons(query: String): List<NewPokemon> {
        if (query.isBlank()) {
            return pokemons()
        }
        return pokemons().filter { it.name.has(query) }
    }

    override suspend fun pokemonDetail(id: String): NewPokemonDetail {
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
