package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.remote.dto.response.pokemon.toData
import poke.rogue.helper.remote.service.PokeDexService

class RemotePokemonListDataSource(
    private val pokeDexService: PokeDexService,
) {
    fun pokemons(): List<Pokemon> = pokeDexService.pokemons().data.toData()
}
