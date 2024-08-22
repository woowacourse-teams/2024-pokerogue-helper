package poke.rogue.helper.remote.service

import poke.rogue.helper.remote.dto.base.ApiResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityDetailResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityDetailResponse2
import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityResponse2
import retrofit2.http.GET
import retrofit2.http.Path

interface AbilityService {
    @GET("api/v1/abilities2")
    suspend fun abilities2(): ApiResponse<List<AbilityResponse2>>

    @GET("api/v1/ability2/{id}")
    suspend fun ability2(
        @Path("id") id: String,
    ): ApiResponse<AbilityDetailResponse2>

    @GET("api/v1/abilities")
    suspend fun abilities(): ApiResponse<List<AbilityResponse>>

    @GET("api/v1/ability/{id}")
    suspend fun ability(
        @Path("id") id: Long,
    ): ApiResponse<AbilityDetailResponse>
}
