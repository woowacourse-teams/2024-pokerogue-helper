package poke.rogue.helper.remote.service

import poke.rogue.helper.remote.dto.response.pokemon.PokemonDetailResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeDexService {
    @GET("api/v1/pokemons")
    fun pokemons(): List<PokemonResponse>

    @GET("api/v1/pokemon/{id}")
    fun pokemon(
        @Path("id") id: Long,
    ): PokemonDetailResponse
}
