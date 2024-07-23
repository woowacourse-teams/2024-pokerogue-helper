package poke.rogue.helper.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import poke.rogue.helper.data.datasource.FakePokemonListDataSource
import poke.rogue.helper.data.model.Pokemon

class FakePokemonListRepository(
    private val pokemonListDataSource: FakePokemonListDataSource,
) : PokemonListRepository {
    override fun pokemons(): Flow<List<Pokemon>> =
        flow {
            emit(pokemonListDataSource.pokemons())
        }

    override fun searchedPokemons(query: String): Flow<List<Pokemon>> =
        flow {
            emit(pokemonListDataSource.pokemons(query))
        }
}
