package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.NewPokemon
import poke.rogue.helper.data.model.PokemonDetail

interface DexRepository {
    suspend fun pokemons(): List<NewPokemon>

    suspend fun pokemons(query: String): List<NewPokemon>

    suspend fun pokemonDetail(id: Long): PokemonDetail
}
