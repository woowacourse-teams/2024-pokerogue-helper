package poke.rogue.helper.presentation.battle

import poke.rogue.helper.presentation.battle.model.BattlePredictionUiModel

sealed interface BattleResultUiState {
    data object Idle : BattleResultUiState

    data object Loading : BattleResultUiState

    data class Success(val result: BattlePredictionUiModel) : BattleResultUiState
}

fun BattleResultUiState.isSuccess(): Boolean = this is BattleResultUiState.Success
