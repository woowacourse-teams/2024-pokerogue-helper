package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonFilter
import poke.rogue.helper.data.model.PokemonSort

interface DexRepository {
    suspend fun warmUp()

    suspend fun pokemons(): List<Pokemon>

    suspend fun filteredPokemons(
        name: String = "",
        sort: PokemonSort = PokemonSort.ByDexNumber,
        filters: List<PokemonFilter> = emptyList(),
    ): List<Pokemon>

    suspend fun pokemonDetail(id: String): PokemonDetail
}
