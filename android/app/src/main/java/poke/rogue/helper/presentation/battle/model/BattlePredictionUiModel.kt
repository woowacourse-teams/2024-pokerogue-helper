package poke.rogue.helper.presentation.battle.model

import poke.rogue.helper.data.model.BattlePrediction

data class BattlePredictionUiModel(val power: String, val accuracy: String, val multiplier: String, val calculatedResult: String)

fun BattlePrediction.toUi(format: String = "%.1f"): BattlePredictionUiModel =
    BattlePredictionUiModel(
        power = power.toString(),
        accuracy = String.format(format, accuracy),
        multiplier = String.format(format, multiplier),
        calculatedResult = String.format(format, calculatedResult),
    )
