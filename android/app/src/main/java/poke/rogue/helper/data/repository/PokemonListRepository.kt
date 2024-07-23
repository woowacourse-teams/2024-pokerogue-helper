package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.Pokemon

interface PokemonListRepository {
    fun pokemons(): List<Pokemon>

    fun pokemons(query: String): List<Pokemon>
}
