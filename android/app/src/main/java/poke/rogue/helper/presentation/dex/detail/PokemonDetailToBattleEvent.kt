package poke.rogue.helper.presentation.dex.detail

import poke.rogue.helper.presentation.dex.model.PokemonUiModel

data class PokemonDetailToBattleEvent(
    val battlePopUp: BattlePopUpUiModel,
    val pokemon: PokemonUiModel,
)
