package poke.rogue.helper.data.datasource

import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.exception.getOrThrow
import poke.rogue.helper.data.exception.onFailure
import poke.rogue.helper.data.model.Weather
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.injector.ServiceModule
import poke.rogue.helper.remote.service.BattleService

class RemoteBattleDataSource(
    private val battleService: BattleService,
    private val logger: AnalyticsLogger,
) {
    suspend fun weathers(): List<Weather> =
        battleService.weathers()
            .onFailure {
                logger.logError(throwable, "battleService - weathers() 에서 발생")
            }
            .getOrThrow()
            .map { it.toData() }

    companion object {
        private var instance: RemoteBattleDataSource? = null

        fun instance(): RemoteBattleDataSource {
            return instance
                ?: RemoteBattleDataSource(
                    ServiceModule.battleService(),
                    analyticsLogger(),
                ).also { instance = it }
        }
    }
}
