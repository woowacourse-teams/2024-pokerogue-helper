package poke.rogue.helper.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import poke.rogue.helper.data.datasource.FakePokemonListDataSource
import poke.rogue.helper.data.model.Pokemon

class FakePokemonListRepository(
    private val pokemonListDataSource: FakePokemonListDataSource,
) : PokemonListRepository {
    override fun pokemons(): List<Pokemon> = pokemonListDataSource.pokemons()

    override fun searchPokemons(query: String): Flow<List<Pokemon>> {
        return flow {
            emit(pokemons().filter { it.name.contains(query, ignoreCase = true) })
        }
    }
}
