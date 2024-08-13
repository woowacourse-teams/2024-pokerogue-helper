package poke.rogue.helper.data.datasource

import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.exception.getOrThrow
import poke.rogue.helper.data.exception.onFailure
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.injector.ServiceModule
import poke.rogue.helper.remote.service.PokeDexService

class RemoteDexDataSource(
    private val pokeDexService: PokeDexService,
    private val logger: AnalyticsLogger,
) {
    suspend fun pokemons(): List<Pokemon> =
        pokeDexService.pokemons()
            .onFailure {
                logger.logError(throwable, "pokeDexService - pokemons() 에서 발생")
            }
            .getOrThrow()
            .toData()

    suspend fun pokemon(id: Long): PokemonDetail =
        pokeDexService.pokemon(id)
            .onFailure {
                logger.logError(throwable, "pokeDexService - pokemon($id) 에서 발생")
            }
            .getOrThrow()
            .toData(id)

    companion object {
        private var instance: RemoteDexDataSource? = null

        fun instance(): RemoteDexDataSource {
            return instance ?: RemoteDexDataSource(
                ServiceModule.pokeDexService(),
                analyticsLogger(),
            ).also {
                instance = it
            }
        }
    }
}
