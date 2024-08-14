package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonFilter
import poke.rogue.helper.data.model.PokemonSort

interface DexRepository {
    suspend fun pokemons(): List<Pokemon>

    suspend fun filteredPokemons(
        name: String = "",
        sort: PokemonSort = PokemonSort.ByDexNumber,
        filter: PokemonFilter = PokemonFilter.ByAll,
    ): List<Pokemon>

    suspend fun pokemonDetail(id: Long): PokemonDetail
}
