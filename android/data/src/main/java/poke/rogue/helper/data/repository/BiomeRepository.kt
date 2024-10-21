package poke.rogue.helper.data.repository

import kotlinx.coroutines.flow.Flow
import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.BiomeDetail

interface BiomeRepository {
    suspend fun biomes(): List<Biome>

    suspend fun biomes(query: String): List<Biome>

    suspend fun biomeDetail(id: String): BiomeDetail

    suspend fun saveNavigationMode(isBattleNavigationMode: Boolean)

    fun isBattleNavigationModeStream(): Flow<Boolean>
}
