package poke.rogue.helper.data.repository

import android.content.Context
import poke.rogue.helper.data.datasource.LocalDexDataSource
import poke.rogue.helper.data.datasource.RemoteDexDataSource
import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonBiome
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonFilter
import poke.rogue.helper.data.model.PokemonSort
import poke.rogue.helper.stringmatcher.has

class DefaultDexRepository(
    private val remotePokemonDataSource: RemoteDexDataSource,
    private val localPokemonDataSource: LocalDexDataSource,
    private val biomeRepository: BiomeRepository,
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
        val pokemonDetail = remotePokemonDataSource.pokemon(id)

        val pokemonBiomes =
            pokemonDetail.biomes
                .flatMap { pokemonBiome ->
                    allBiomes
                        .filter { biome -> pokemonBiome.id == biome.id }
                        .map { filteredBiome -> filteredBiome.toPokemonBiome() }
                }

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

        fun init(context: Context) {
            instance =
                DefaultDexRepository(
                    RemoteDexDataSource.instance(),
                    LocalDexDataSource.instance(context),
                    DefaultBiomeRepository.instance(),
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