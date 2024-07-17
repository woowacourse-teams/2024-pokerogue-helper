package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.Pokemon

interface PokemonListDataSource {
    fun pokemons(): List<Pokemon>
}
