package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemotePokemonListDataSource
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.stringmatcher.has

class DefaultPokemonListRepository(
    private val pokemonListDataSource: RemotePokemonListDataSource,
) : PokemonListRepository {
    private var cachedPokemons: List<Pokemon> = emptyList()

    override suspend fun pokemons(): List<Pokemon> {
        if (cachedPokemons.isEmpty()) {
            cachedPokemons = pokemonListDataSource.pokemons()
        }
        return cachedPokemons
    }

    override suspend fun pokemons(query: String): List<Pokemon> {
        if (query.isBlank()) {
            return pokemons()
        }
        return pokemons().filter { it.name.has(query) }
    }

    companion object {
        private var instance: PokemonListRepository? = null

        fun instance(): PokemonListRepository {
            return instance
                ?: DefaultPokemonListRepository(RemotePokemonListDataSource.instance()).also {
                    instance = it
                }
        }
    }
}
