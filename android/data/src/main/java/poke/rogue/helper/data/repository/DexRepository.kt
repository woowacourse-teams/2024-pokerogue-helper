package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.NewPokemonDetail
import poke.rogue.helper.data.model.Pokemon

interface DexRepository {
    suspend fun pokemons(): List<Pokemon>

    suspend fun pokemons(query: String): List<Pokemon>

    suspend fun pokemonDetail(id: String): NewPokemonDetail
}
