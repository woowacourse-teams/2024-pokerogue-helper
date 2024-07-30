package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.service.PokeDexService

class RemotePokemonListDataSource(
    private val pokeDexService: PokeDexService,
) {
    suspend fun pokemons(): List<Pokemon> = pokeDexService.pokemons().data.toData()
}
