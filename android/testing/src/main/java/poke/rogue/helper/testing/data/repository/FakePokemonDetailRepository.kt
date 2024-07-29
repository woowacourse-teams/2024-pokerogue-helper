package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.FakePokemonDetailDataSource
import poke.rogue.helper.data.model.PokemonDetail

class FakePokemonDetailRepository(
    private val fakePokemonDetailDataSource: FakePokemonDetailDataSource,
) : PokemonDetailRepository {
    override suspend fun pokemonDetail(id: Long): PokemonDetail = fakePokemonDetailDataSource.pokemonDetail(id)
}
