package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemotePokemonListDataSource
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.stringmatcher.has

class DefaultPokemonListRepository(
    private val pokemonListDataSource: RemotePokemonListDataSource,
) : PokemonListRepository {
    override suspend fun pokemons(): List<Pokemon> = pokemonListDataSource.pokemons()

    override suspend fun pokemons(query: String): List<Pokemon> = pokemonListDataSource.pokemons().filter {
        it.name.has(query)
    }
}
