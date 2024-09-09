package poke.rogue.helper.presentation.battle.model

import poke.rogue.helper.data.model.BattlePrediction
import poke.rogue.helper.presentation.battle.model.BattlePredictionUiModel.Companion.NO_EFFECT_VALUE

data class BattlePredictionUiModel(val power: String, val accuracy: String, val multiplier: String, val calculatedResult: String) {
    companion object {
        const val NO_EFFECT_VALUE = "-"
    }
}

fun BattlePrediction.toUi(format: String = "%.1f"): BattlePredictionUiModel {
    val formattedPower = if (power < 0) NO_EFFECT_VALUE else power.toString()
    val formattedResult = if (calculatedResult < 0) NO_EFFECT_VALUE else String.format(format, calculatedResult)

    return BattlePredictionUiModel(
        power = formattedPower,
        accuracy = String.format(format, accuracy),
        multiplier = String.format(format, multiplier),
        calculatedResult = formattedResult,
    )
}
