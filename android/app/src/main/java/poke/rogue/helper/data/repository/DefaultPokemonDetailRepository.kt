package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemotePokemonDetailDataSource
import poke.rogue.helper.data.model.PokemonDetail

class DefaultPokemonDetailRepository(
    private val remotePokemonDetailDataSource: RemotePokemonDetailDataSource,
) : PokemonDetailRepository {
    override suspend fun pokemonDetail(id: Long): PokemonDetail = remotePokemonDetailDataSource.pokemon(id)
}
