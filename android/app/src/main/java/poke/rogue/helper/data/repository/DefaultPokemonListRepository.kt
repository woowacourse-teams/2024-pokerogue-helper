package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemotePokemonListDataSource
import poke.rogue.helper.data.model.Pokemon

class DefaultPokemonListRepository(
    private val pokemonListDataSource: RemotePokemonListDataSource,
) : PokemonListRepository {
    override suspend fun pokemons(): List<Pokemon> = pokemonListDataSource.pokemons()

    override fun pokemons(query: String): List<Pokemon> {
        TODO("Not yet implemented")
    }
}
