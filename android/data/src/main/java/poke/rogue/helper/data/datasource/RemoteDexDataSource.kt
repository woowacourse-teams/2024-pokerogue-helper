package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.injector.ServiceModule
import poke.rogue.helper.remote.service.PokeDexService

class RemoteDexDataSource(
    private val pokeDexService: PokeDexService,
) {
    suspend fun pokemons(): List<Pokemon> = pokeDexService.pokemons().data.toData()

    suspend fun pokemon(id: Long): PokemonDetail = pokeDexService.pokemon(id).data.toData(id)

    companion object {
        private var instance: RemoteDexDataSource? = null

        fun instance(): RemoteDexDataSource {
            return instance ?: RemoteDexDataSource(ServiceModule.pokeDexService()).also {
                instance = it
            }
        }
    }
}
