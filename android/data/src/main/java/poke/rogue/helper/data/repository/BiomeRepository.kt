package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.BiomeDetail

interface BiomeRepository {
    suspend fun biomes(): List<Biome>

    suspend fun biomeDetail(id: String): BiomeDetail
}
