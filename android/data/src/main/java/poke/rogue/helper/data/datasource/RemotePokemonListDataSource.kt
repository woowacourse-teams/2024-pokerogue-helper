package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.ServiceModule
import poke.rogue.helper.remote.service.PokeDexService

class RemotePokemonListDataSource(
    private val pokeDexService: PokeDexService,
) {
    suspend fun pokemons(): List<Pokemon> = pokeDexService.pokemons().data.toData()

    companion object {
        private var instance: RemotePokemonListDataSource? = null

        fun instance(): RemotePokemonListDataSource {
            return instance ?: RemotePokemonListDataSource(ServiceModule.pokeDexService()).also {
                instance = it
            }
        }
    }
}
