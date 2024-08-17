package poke.rogue.helper.remote.service

import poke.rogue.helper.remote.dto.base.ApiResponse
import poke.rogue.helper.remote.dto.response.biomes.BiomesResponse
import retrofit2.http.GET

interface BiomeService {

    @GET("api/v1/biomes")
    suspend fun biomes(): ApiResponse<List<BiomesResponse>>

    @GET("api/v1/biome/{id}")
    suspend fun biome(): ApiResponse<BiomesResponse>
}
