package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.PokemonDetail

interface PokemonDetailRepository {
    suspend fun pokemonDetail(id: Long): PokemonDetail
}
