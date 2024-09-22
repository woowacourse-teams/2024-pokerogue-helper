package poke.rogue.helper.data.repository

import android.content.Context
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.cache.GlideImageCacher
import poke.rogue.helper.data.cache.ImageCacher
import poke.rogue.helper.data.datasource.LocalDexDataSource
import poke.rogue.helper.data.datasource.RemoteDexDataSource
import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonBiome
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonFilter
import poke.rogue.helper.data.model.PokemonSort
import poke.rogue.helper.data.utils.logPokemonDetail
import poke.rogue.helper.stringmatcher.has

class DefaultDexRepository(
    private val remotePokemonDataSource: RemoteDexDataSource,
    private val localPokemonDataSource: LocalDexDataSource,
    private val imageCacher: ImageCacher,
    private val biomeRepository: BiomeRepository,
    private val analyticsLogger: AnalyticsLogger,
) : DexRepository {
    private var cachedPokemons: List<Pokemon> = emptyList()

    override suspend fun warmUp() {
        if (localPokemonDataSource.pokemons().isEmpty()) {
            val pokemons = remotePokemonDataSource.pokemons2()
            cachePokemonData(pokemons)
        }
        cachedPokemons = localPokemonDataSource.pokemons()
    }

    private suspend fun cachePokemonData(pokemons: List<Pokemon>) =
        coroutineScope {
            val urls = pokemons.take(PLELOAD_POKEMON_COUNT).map { it.imageUrl }
            launch {
                imageCacher.cacheImages(urls)
            }
            launch {
                localPokemonDataSource.savePokemons(pokemons)
            }
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

    override suspend fun pokemonDetail(id: String): PokemonDetail {
        val allBiomes = biomeRepository.biomes()
        return coroutineScope {
            return@coroutineScope pokemonDetail(id, allBiomes).also {
                val pokemonName = it.pokemon.name + " " + it.pokemon.formName
                analyticsLogger.logPokemonDetail(id, pokemonName)
            }
        }
    }

    private suspend fun pokemonDetail(
        id: String,
        allBiomes: List<Biome>,
    ): PokemonDetail {
        val pokemonDetail = remotePokemonDataSource.pokemon(id)
        val pokemonDetailIds = pokemonDetail.biomes.map(PokemonBiome::id)
        val pokemonBiomes =
            allBiomes
                .filter { biome -> biome.id in pokemonDetailIds }
                .toPokemonBiome()

        return pokemonDetail.copy(
            biomes = pokemonBiomes,
        )
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
        const val PLELOAD_POKEMON_COUNT = 24

        fun init(context: Context) {
            GlideImageCacher.init(context)
            instance =
                DefaultDexRepository(
                    RemoteDexDataSource.instance(),
                    LocalDexDataSource.instance(context),
                    GlideImageCacher.instance(),
                    DefaultBiomeRepository.instance(),
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

private fun Biome.toPokemonBiome(): PokemonBiome =
    PokemonBiome(
        id = id,
        name = name,
        imageUrl = image,
        pokemonType = pokemonType,
    )

private fun List<Biome>.toPokemonBiome(): List<PokemonBiome> = map(Biome::toPokemonBiome)
