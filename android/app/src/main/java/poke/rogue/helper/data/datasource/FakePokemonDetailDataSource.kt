package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.PokemonDetail

class FakePokemonDetailDataSource : PokemonDetailDataSource {
    override fun pokemonDetail(id: Long): PokemonDetail = PokemonDetail.DUMMY
}
