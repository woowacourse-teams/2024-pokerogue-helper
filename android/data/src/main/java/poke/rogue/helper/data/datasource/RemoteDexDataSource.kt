package poke.rogue.helper.data.datasource

import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.exception.getOrThrow
import poke.rogue.helper.data.exception.onFailure
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonDetail2
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse2
import poke.rogue.helper.remote.injector.ServiceModule
import poke.rogue.helper.remote.service.PokeDexService

class RemoteDexDataSource(
    private val pokeDexService: PokeDexService,
    private val logger: AnalyticsLogger,
) {
    @Deprecated("pokemons2() 사용 권장 - 근데 서버에서 아직 데이터 안옴")
    suspend fun pokemons(): List<Pokemon> =
        pokeDexService.pokemons()
            .onFailure {
                logger.logError(throwable, "pokeDexService - pokemons() 에서 발생")
            }
            .getOrThrow()
            .toData()

    suspend fun pokemons2(): List<Pokemon> =
        pokeDexService.pokemons2()
            .onFailure {
                logger.logError(throwable, "pokeDexService - pokemons2() 에서 발생")
            }
            .getOrThrow()
            .map(PokemonResponse2::toData)

    suspend fun pokemon(id: String): PokemonDetail =
        pokeDexService.pokemon(id.toLong())
            .onFailure {
                logger.logError(throwable, "pokeDexService - pokemon($id) 에서 발생")
            }
            .getOrThrow()
            .toData(id.toLong())

    suspend fun pokemon2(id: String): PokemonDetail2 =
        pokeDexService.pokemon2(id)
            .onFailure {
                logger.logError(throwable, "pokeDexService - pokemon2($id) 에서 발생")
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
