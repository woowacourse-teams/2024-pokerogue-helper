package poke.rogue.helper.presentation.battle

import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.model.WeatherUiModel

data class BattleSelectionsState(
    val weather: BattleSelectionUiState<WeatherUiModel>,
    val minePokemon: BattleSelectionUiState<PokemonSelectionUiModel>,
    val skill: BattleSelectionUiState<SkillSelectionUiModel>,
    val opponentPokemon: BattleSelectionUiState<PokemonSelectionUiModel>,
) {
    val allSelected: Boolean
        get() =
            minePokemon.isSelected() && skill.isSelected() && opponentPokemon.isSelected() && weather.isSelected()

    companion object {
        val DEFAULT =
            BattleSelectionsState(
                BattleSelectionUiState.Empty,
                BattleSelectionUiState.Empty,
                BattleSelectionUiState.Empty,
                BattleSelectionUiState.Empty,
            )
    }
}
