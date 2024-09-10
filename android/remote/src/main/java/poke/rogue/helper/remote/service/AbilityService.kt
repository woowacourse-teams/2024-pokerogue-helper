package poke.rogue.helper.remote.service

import poke.rogue.helper.remote.dto.base.ApiResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityDetailResponse2
import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AbilityService {
    @GET("api/v1/abilities2")
    suspend fun abilities(): ApiResponse<List<AbilityResponse>>

    @GET("api/v1/ability2/{id}")
    suspend fun ability(
        @Path("id") id: String,
    ): ApiResponse<AbilityDetailResponse2>
}
