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
            listOf(minePokemon, skill, opponentPokemon, weather).all { it.isSelected() }

    val isMinePokemonSelected: Boolean
        get() = minePokemon.isSelected()

    val isOpponentPokemonSelected: Boolean
        get() = opponentPokemon.isSelected()

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
