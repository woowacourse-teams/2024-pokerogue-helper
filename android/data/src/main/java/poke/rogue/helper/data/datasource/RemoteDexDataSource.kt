package poke.rogue.helper.data.datasource

import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.data.exception.getOrThrow
import poke.rogue.helper.data.exception.onFailure
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse2
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
        pokeDexService.pokemon(id)
            .onFailure {
                logger.logError(throwable, "pokeDexService - pokemon2($id) 에서 발생")
            }
            .getOrThrow()
            .toData(id)
}
