package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.Pokemon

interface DexRepository {
    suspend fun pokemons(): List<Pokemon>

    suspend fun pokemons(query: String): List<Pokemon>

    suspend fun pokemonDetail(id: String): PokemonDetail
}
