package poke.rogue.helper.data.datasource

import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.exception.getOrThrow
import poke.rogue.helper.data.exception.onFailure
import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.BiomeDetail
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.injector.ServiceModule
import poke.rogue.helper.remote.service.BiomeService

class RemoteBiomeDataSource(
    private val biomeService: BiomeService,
    private val logger: AnalyticsLogger,
) {
    suspend fun biomes(): List<Biome> =
        biomeService.biomes()
            .onFailure {
                logger.logError(throwable, "biomeService - biomes() 에서 발생")
            }
            .getOrThrow()
            .map { it.toData() }

    suspend fun biomeDetail(id: String): BiomeDetail =
        biomeService.biome(id)
            .onFailure {
                logger.logError(throwable, "biomeService - biome($id) 에서 발생")
            }
            .getOrThrow()
            .toData()

    companion object {
        private var instance: RemoteBiomeDataSource? = null

        fun instance(): RemoteBiomeDataSource {
            return instance
                ?: RemoteBiomeDataSource(
                    ServiceModule.biomeService(),
                    analyticsLogger(),
                ).also { instance = it }
        }
    }
}
