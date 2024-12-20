package poke.rogue.helper.data.datasource

import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.data.model.toEntity
import poke.rogue.helper.local.dao.PokemonDao

class LocalDexDataSource(
    private val pokemonDao: PokemonDao,
    private val logger: AnalyticsLogger,
) {
    suspend fun pokemons(): List<Pokemon> =
        runCatching {
            pokemonDao.pokemons().map { it.toData() }
        }.onFailure {
            logger.logError(it, "LocalDexDataSource - pokemons() 에서 발생")
        }.getOrThrow()

    suspend fun savePokemons(pokemons: List<Pokemon>): Unit =
        runCatching {
            pokemonDao.savePokemons(pokemons.map { it.toEntity() })
        }.onFailure {
            logger.logError(it, "LocalDexDataSource - savePokemons() 에서 발생")
        }.getOrThrow()

    suspend fun clear(): Unit =
        runCatching {
            pokemonDao.clear()
        }.onFailure {
            logger.logError(it, "LocalDexDataSource - clearPokemons() 에서 발생")
        }.getOrThrow()
}
