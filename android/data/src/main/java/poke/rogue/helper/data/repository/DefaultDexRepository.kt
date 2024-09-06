package poke.rogue.helper.data.repository

import android.content.Context
import kotlinx.coroutines.coroutineScope
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.datasource.LocalDexDataSource
import poke.rogue.helper.data.datasource.RemoteDexDataSource
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonFilter
import poke.rogue.helper.data.model.PokemonSort
import poke.rogue.helper.data.utils.logPokemonDetail
import poke.rogue.helper.stringmatcher.has

class DefaultDexRepository(
    private val remotePokemonDataSource: RemoteDexDataSource,
    private val localPokemonDataSource: LocalDexDataSource,
    private val analyticsLogger: AnalyticsLogger,
) : DexRepository {
    private var cachedPokemons: List<Pokemon> = emptyList()

    override suspend fun warmUp() {
        if (localPokemonDataSource.pokemons().isEmpty()) {
            localPokemonDataSource.savePokemons(remotePokemonDataSource.pokemons2())
        }
        cachedPokemons = localPokemonDataSource.pokemons()
    }

    override suspend fun pokemons(): List<Pokemon> {
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

    override suspend fun pokemonDetail(id: String): PokemonDetail =
        coroutineScope {
            return@coroutineScope remotePokemonDataSource.pokemon(id).also {
                val pokemonName = it.pokemon.name + " " + it.pokemon.formName
                analyticsLogger.logPokemonDetail(id, pokemonName)
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
                    analyticsLogger(),
                )
        }

        fun instance(): DexRepository {
            return requireNotNull(instance) {
                "DexRepository is not initialized"
            }
        }
    }
}
