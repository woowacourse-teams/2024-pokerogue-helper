package poke.rogue.helper.presentation.battle.selection

import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel

interface BattleSelectionHandler {
    fun selectPokemon(pokemon: PokemonSelectionUiModel)

    fun selectSkill(skill: SkillSelectionUiModel)
}
