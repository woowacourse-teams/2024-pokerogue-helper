package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.Pokemon

interface PokemonListRepository {
    suspend fun pokemons(): List<Pokemon>

    suspend fun pokemons(query: String): List<Pokemon>
}
