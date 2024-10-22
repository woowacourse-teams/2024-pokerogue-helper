package poke.rogue.helper.presentation.dex.detail

import poke.rogue.helper.presentation.dex.model.PokemonUiModel

sealed class NavigateToBattleEvent {
    data class WithMyPokemon(val pokemon: PokemonUiModel) : NavigateToBattleEvent()

    data class WithOpponentPokemon(val pokemon: PokemonUiModel) : NavigateToBattleEvent()
}
