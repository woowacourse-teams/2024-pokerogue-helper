package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.NewPokemon
import poke.rogue.helper.data.model.NewPokemonDetail

interface DexRepository {
    suspend fun pokemons(): List<NewPokemon>

    suspend fun pokemons(query: String): List<NewPokemon>

    suspend fun pokemonDetail(id: String): NewPokemonDetail
}
