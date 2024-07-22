package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.FakePokemonListDataSource
import poke.rogue.helper.data.model.Pokemon

class FakePokemonListRepository(
    private val pokemonListDataSource: FakePokemonListDataSource,
) : PokemonListRepository {
    override fun pokemons(): List<Pokemon> = pokemonListDataSource.pokemons()
}
