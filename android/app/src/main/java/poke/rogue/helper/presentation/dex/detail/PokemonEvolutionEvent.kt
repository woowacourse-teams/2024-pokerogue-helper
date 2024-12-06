package poke.rogue.helper.presentation.dex.detail

sealed class PokemonEvolutionEvent {
    data class NavigateToPokemonDetail(val pokemonId: String) : PokemonEvolutionEvent()

    data class SameWithCurrentPokemon(val pokemonName: String) : PokemonEvolutionEvent()
}
