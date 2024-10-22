package poke.rogue.helper.presentation.battle.selection.pokemon

import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel

interface PokemonSelectionHandler {
    fun selectPokemon(selected: PokemonSelectionUiModel)
}
