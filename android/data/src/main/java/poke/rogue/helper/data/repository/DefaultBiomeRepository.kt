package poke.rogue.helper.data.repository

import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.datasource.RemoteBiomeDataSource
import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.BiomeDetail
import poke.rogue.helper.data.utils.logBiomeDetail

class DefaultBiomeRepository(
    private val remoteBiomeDataSource: RemoteBiomeDataSource,
    private val analyticsLogger: AnalyticsLogger,
) : BiomeRepository {
    private var cachedBiomes: List<Biome> = emptyList()

    override suspend fun biomes(): List<Biome> {
        if (cachedBiomes.isEmpty()) {
            cachedBiomes = remoteBiomeDataSource.biomes()
        }
        return cachedBiomes
    }

    override suspend fun biomeDetail(id: String): BiomeDetail {
        return remoteBiomeDataSource.biomeDetail(id).also {
            analyticsLogger.logBiomeDetail(id, it.name)
        }
    }

    companion object {
        private var instance: DefaultBiomeRepository? = null

        fun instance(): DefaultBiomeRepository {
            return instance
                ?: DefaultBiomeRepository(
                    RemoteBiomeDataSource.instance(),
                    analyticsLogger(),
                ).also {
                    instance = it
                }
        }
    }
}
