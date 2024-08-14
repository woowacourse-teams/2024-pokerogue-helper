package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemoteDexDataSource
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonFilter
import poke.rogue.helper.data.model.PokemonSort
import poke.rogue.helper.stringmatcher.has

class DefaultDexRepository(
    private val dexDataSource: RemoteDexDataSource,
) : DexRepository {
    private var cachedPokemons: List<Pokemon> = emptyList()
    private var cachedPokemonDetails: MutableMap<Long, PokemonDetail> = mutableMapOf()

    override suspend fun pokemons(): List<Pokemon> {
        if (cachedPokemons.isEmpty()) {
            cachedPokemons = dexDataSource.pokemons()
        }
        return cachedPokemons
    }

    override suspend fun pokemons(
        name: String,
        sort: PokemonSort,
        filter: PokemonFilter,
    ): List<Pokemon> {
        val filteredPokemons = pokemons().toFilteredPokemons(sort, filter)
        if (name.isBlank()) {
            return filteredPokemons
        }
        return filteredPokemons.filter { it.name.has(name) }
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

    private fun List<Pokemon>.toFilteredPokemons(
        sort: PokemonSort,
        filter: PokemonFilter,
    ): List<Pokemon> {
        return this.sortedWith(sort)
            .filter {
                when (filter) {
                    is PokemonFilter.ByAll -> true
                    is PokemonFilter.ByType -> {
                        it.types.contains(filter.type)
                    }

                    is PokemonFilter.ByGeneration -> {
                        it.generation == filter.generation
                    }
                }
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
