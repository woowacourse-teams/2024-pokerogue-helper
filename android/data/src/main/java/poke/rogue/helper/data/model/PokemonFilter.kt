package poke.rogue.helper.data.model

sealed class PokemonFilter {
    data class ByType(val type: Type) : PokemonFilter()

    data class ByGeneration(val generation: PokemonGeneration) : PokemonFilter()
}
