package poke.rogue.helper.remote.service

import poke.rogue.helper.remote.dto.base.ApiResponse
import poke.rogue.helper.remote.dto.response.BaseResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonDetailResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeDexService {
    @GET("api/v1/pokemons")
    suspend fun pokemons(): BaseResponse<List<PokemonResponse>>

    @GET("api/v1/pokemon/{id}")
    suspend fun pokemon(
        @Path("id") id: Long,
    ): BaseResponse<PokemonDetailResponse>
}

interface PokeDexService2 {
    @GET("api/v1/pokemons")
    suspend fun pokemons(): ApiResponse<List<PokemonResponse>>

    @GET("api/v1/pokemon/{id}")
    suspend fun pokemon(
        @Path("id") id: Long = 1,
    ): ApiResponse<PokemonDetailResponse>
}
