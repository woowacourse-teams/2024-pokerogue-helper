package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemoteBiomeDataSource
import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.BiomeDetail

class DefaultBiomeRepository(
    private val remoteBiomeDataSource: RemoteBiomeDataSource
) : BiomeRepository {
    private var cachedBiomes: List<Biome> = emptyList()
    private var cachedBiomesDetails: MutableMap<String, BiomeDetail> = mutableMapOf()

    override suspend fun biomes(): List<Biome> {
        if (cachedBiomes.isEmpty()) {
            cachedBiomes = remoteBiomeDataSource.biomes()
        }
        return cachedBiomes
    }

    override suspend fun biomeDetail(id: String): BiomeDetail {
        val cached = cachedBiomesDetails[id]
        if (cached != null) {
            return cached
        }
        return remoteBiomeDataSource.biomeDetail(id).also {
            cachedBiomesDetails[id] = it
        }
    }

    companion object {
        private var instance: DefaultBiomeRepository? = null

        fun instance(): DefaultBiomeRepository {
            return instance
                ?: DefaultBiomeRepository(RemoteBiomeDataSource.instance()).also {
                    instance = it
                }
        }
    }
}
