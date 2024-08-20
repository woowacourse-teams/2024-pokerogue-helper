package poke.rogue.helper.data.datasource

import android.content.Context
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
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

    suspend fun savePokemons(pokemons: List<Pokemon>) =
        runCatching {
            pokemonDao.savePokemons(pokemons.map { it.toEntity() })
        }.onFailure {
            logger.logError(it, "LocalDexDataSource - savePokemons() 에서 발생")
        }.getOrThrow()

    companion object {
        private var instance: LocalDexDataSource? = null

        fun instance(context: Context): LocalDexDataSource {
            return instance ?: LocalDexDataSource(
                PokemonDao.instance(context),
                analyticsLogger(),
            ).also {
                instance = it
            }
        }
    }
}
