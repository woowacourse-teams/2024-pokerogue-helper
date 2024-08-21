package poke.rogue.helper.remote.service

import poke.rogue.helper.remote.dto.base.ApiResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonDetailResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonDetailResponse2
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse2
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeDexService {
    @GET("api/v1/pokemons2")
    suspend fun pokemons2(): ApiResponse<List<PokemonResponse2>>

    @GET("api/v1/pokemons")
    suspend fun pokemons(): ApiResponse<List<PokemonResponse>>

    @GET("api/v1/pokemon/{id}")
    suspend fun pokemon(
        @Path("id") id: Long = 1,
    ): ApiResponse<PokemonDetailResponse>

    @GET("api/v1/pokemon/{id}")
    suspend fun pokemon2(
        @Path("id") id: String,
    ): ApiResponse<PokemonDetailResponse2>
}
