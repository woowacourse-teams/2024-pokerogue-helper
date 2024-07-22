package poke.rogue.helper.data.repository

import kotlinx.coroutines.flow.Flow
import poke.rogue.helper.data.model.Pokemon

interface PokemonListRepository {
    fun pokemons(): List<Pokemon>

    fun searchPokemons(query: String): Flow<List<Pokemon>>
}
