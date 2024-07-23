package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.FakePokemonListDataSource
import poke.rogue.helper.data.model.Pokemon

class FakePokemonListRepository(
    private val pokemonListDataSource: FakePokemonListDataSource,
) : PokemonListRepository {
    override suspend fun pokemons(): List<Pokemon> = pokemonListDataSource.pokemons()

    override suspend fun pokemons(query: String): List<Pokemon> = pokemonListDataSource.pokemons(query)
}
