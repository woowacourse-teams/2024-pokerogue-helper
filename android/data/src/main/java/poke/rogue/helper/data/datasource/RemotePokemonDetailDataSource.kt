package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.service.PokeDexService

class RemotePokemonDetailDataSource(
    private val pokeDexService: PokeDexService,
) {
    suspend fun pokemon(id: Long): PokemonDetail = pokeDexService.pokemon(id).data.toData(id)
}
