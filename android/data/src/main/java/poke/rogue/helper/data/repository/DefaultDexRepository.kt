package poke.rogue.helper.data.repository

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.cache.ImageCacher
import poke.rogue.helper.data.datasource.LocalDexDataSource
import poke.rogue.helper.data.datasource.LocalVersionDataSource
import poke.rogue.helper.data.datasource.RemoteDexDataSource
import poke.rogue.helper.data.datasource.RemoteVersionDataSource
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
    private val localVersionDataSource: LocalVersionDataSource,
    private val remoteVersionService: RemoteVersionDataSource,
) : DexRepository {
    private var cachedPokemons: List<Pokemon> = emptyList()

    override suspend fun warmUp() {
        val localVersion = localVersionDataSource.databaseVersionStream().firstOrNull()
        val remoteVersion = remoteVersionService.databaseVersion()
        val shouldUpdateDatabase = shouldUpdateDatabaseVersion(localVersion, remoteVersion)

        if (shouldUpdateDatabase) {
            localVersionDataSource.saveDatabaseVersion(remoteVersion)
            val pokemons = remotePokemonDataSource.pokemons2()
            cachePokemons(pokemons)
            return
        }

        val emptyDiskCache = localPokemonDataSource.pokemons().isEmpty()
        if (emptyDiskCache) {
            cachedPokemons = remotePokemonDataSource.pokemons2()
            return
        }

        cachedPokemons = localPokemonDataSource.pokemons()
    }

    private fun shouldUpdateDatabaseVersion(
        localVersion: Int?,
        remoteVersion: Int,
    ): Boolean {
        return (localVersion ?: 0) < remoteVersion
    }

    private suspend fun cachePokemons(pokemons: List<Pokemon>) =
        coroutineScope {
            val urls = pokemons.take(PLELOAD_POKEMON_COUNT).map { it.imageUrl }
            launch {
                imageCacher.cacheImages(urls)
            }
            launch {
                localPokemonDataSource.clear()
                localPokemonDataSource.savePokemons(pokemons)
            }
        }.also {
            cachedPokemons = pokemons
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
    ): List<Pokemon> =
        if (name.isBlank()) {
            pokemons()
        } else {
            pokemons().filter { it.name.has(name) }
        }.toFilteredPokemons(sort, filters)

    override suspend fun pokemonDetail(id: String): PokemonDetail {
        val allBiomes = biomeRepository.biomes()
        return coroutineScope {
            return@coroutineScope pokemonDetail(id, allBiomes).also {
                val pokemonName = it.pokemon.name + " " + it.pokemon.formName
                analyticsLogger.logPokemonDetail(id, pokemonName)
            }
        }
    }

    override suspend fun pokemon(id: String): Pokemon {
        cachedPokemons.find { it.id == id }?.let {
            return it
        }
        return pokemons().find { it.id == id } ?: error("아이디에 해당하는 포켓몬이 존재하지 않습니다. id : $id")
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
    ): List<Pokemon> =
        this
            .filter { pokemon ->
                pokemonFilters.all { pokemonFilter ->
                    when (pokemonFilter) {
                        is PokemonFilter.ByType -> pokemon.types.contains(pokemonFilter.type)
                        is PokemonFilter.ByGeneration -> pokemon.generation == pokemonFilter.generation
                    }
                }
            }.sortedWith(sort)

    companion object {
        private var instance: DexRepository? = null
        const val PLELOAD_POKEMON_COUNT = 24

        fun init() {
            instance =
                DefaultDexRepository(
                    remotePokemonDataSource = getKoin().get(),
                    localPokemonDataSource = getKoin().get(),
                    imageCacher = getKoin().get(),
                    biomeRepository = DefaultBiomeRepository.instance(),
                    analyticsLogger = analyticsLogger(),
                    localVersionDataSource = getKoin().get(),
                    remoteVersionService = getKoin().get(),
                )
        }

        fun instance(): DexRepository =
            requireNotNull(instance) {
                "DexRepository is not initialized"
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
