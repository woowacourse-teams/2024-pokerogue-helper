package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.PokemonDetail

interface PokemonDetailDataSource {
    fun pokemonDetail(id: Long): PokemonDetail
}
