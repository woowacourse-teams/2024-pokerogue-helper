package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.PokemonDetail

interface PokemonDetailRepository {
    fun pokemonDetail(id: Long): PokemonDetail
}
