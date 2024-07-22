package poke.rogue.helper.data.repository

import kotlinx.coroutines.flow.Flow
import poke.rogue.helper.data.model.Pokemon

interface PokemonListRepository {
    fun pokemons(): Flow<List<Pokemon>>

    fun searchedPokemons(query: String): Flow<List<Pokemon>>
}
