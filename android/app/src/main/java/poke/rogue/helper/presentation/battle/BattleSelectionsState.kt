package poke.rogue.helper.presentation.battle

import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.model.WeatherUiModel

data class BattleSelectionsState(
    val weather: WeatherUiModel,
    val minePokemon: BattleSelectionUiState<PokemonSelectionUiModel>,
    val skill: BattleSelectionUiState<SkillSelectionUiModel>,
    val opponentPokemon: BattleSelectionUiState<PokemonSelectionUiModel>,
) {
    val allSelected: Boolean
        get() =
            minePokemon.isSelected() && skill.isSelected() && opponentPokemon.isSelected()

    companion object {
        val DEFAULT =
            BattleSelectionsState(
                WeatherUiModel.DEFAULT_SELECTED,
                BattleSelectionUiState.Empty,
                BattleSelectionUiState.Empty,
                BattleSelectionUiState.Empty,
            )
    }
}
