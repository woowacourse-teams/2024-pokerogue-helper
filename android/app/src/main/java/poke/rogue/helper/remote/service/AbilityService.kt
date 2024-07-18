package poke.rogue.helper.remote.service

import poke.rogue.helper.remote.dto.response.ability.AbilityDetailResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AbilityService {

    @GET("api/v1/abilities")
    fun abilities(): List<AbilityResponse>

    @GET("api/v1/abilities/{id}")
    fun ability(@Path("id") id: Int): AbilityDetailResponse
}