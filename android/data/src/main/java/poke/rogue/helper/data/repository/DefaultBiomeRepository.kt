package poke.rogue.helper.data.repository

import kotlinx.coroutines.flow.Flow
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.data.datasource.LocalNavigationDataSource
import poke.rogue.helper.data.datasource.RemoteBiomeDataSource
import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.BiomeDetail
import poke.rogue.helper.data.utils.logBiomeDetail
import poke.rogue.helper.stringmatcher.has

class DefaultBiomeRepository(
    private val remoteBiomeDataSource: RemoteBiomeDataSource,
    private val analyticsLogger: AnalyticsLogger,
    private val localNavigationDataSource: LocalNavigationDataSource,
) : BiomeRepository {
    private var cachedBiomes: List<Biome> = emptyList()

    override suspend fun biomes(): List<Biome> {
        if (cachedBiomes.isEmpty()) {
            cachedBiomes = remoteBiomeDataSource.biomes()
        }
        return cachedBiomes
    }

    override suspend fun biomes(query: String): List<Biome> {
        if (query.isBlank()) {
            return biomes()
        }
        return biomes().filter { it.name.has(query) }
    }

    override suspend fun biomeDetail(id: String): BiomeDetail =
        remoteBiomeDataSource.biomeDetail(id).also {
            analyticsLogger.logBiomeDetail(id, it.name)
        }

    override suspend fun saveNavigationMode(isBattleNavigationMode: Boolean) {
        localNavigationDataSource.saveNavigationMode(isBattleNavigationMode)
    }

    override fun isBattleNavigationModeStream(): Flow<Boolean> = localNavigationDataSource.isBattleNavigationModeStream()
}
