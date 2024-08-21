package poke.rogue.helper.remote.service

import poke.rogue.helper.remote.dto.base.ApiResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityDetailResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AbilityService {
    @GET("api/v1/abilities")
    suspend fun abilities(): ApiResponse<List<AbilityResponse>>

    @GET("api/v1/ability/{id}")
    suspend fun ability(
        @Path("id") id: Long,
    ): ApiResponse<AbilityDetailResponse>
}
