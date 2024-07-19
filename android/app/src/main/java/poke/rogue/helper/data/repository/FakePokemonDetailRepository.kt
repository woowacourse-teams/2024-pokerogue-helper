package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.PokemonDetail

class FakePokemonDetailRepository : PokemonDetailRepository {
    override fun pokemonDetail(id: Long): PokemonDetail = PokemonDetail.DUMMY
}
