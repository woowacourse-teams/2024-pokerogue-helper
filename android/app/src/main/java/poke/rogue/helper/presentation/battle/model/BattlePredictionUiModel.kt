package poke.rogue.helper.presentation.battle.model

import androidx.annotation.ColorRes
import poke.rogue.helper.R
import poke.rogue.helper.data.model.BattlePrediction
import poke.rogue.helper.presentation.battle.model.BattlePredictionUiModel.Companion.DEFAULT_NUMBER_FORMAT
import poke.rogue.helper.presentation.battle.model.BattlePredictionUiModel.Companion.NO_EFFECT_VALUE

data class BattlePredictionUiModel(
    val power: String,
    val accuracy: String,
    val multiplier: String,
    val calculatedResult: String,
    @ColorRes val colorRes: Int,
) {
    companion object {
        const val NO_EFFECT_VALUE = "-"
        const val DEFAULT_NUMBER_FORMAT = "%.1f"
    }
}

fun BattlePrediction.toUi(format: String = DEFAULT_NUMBER_FORMAT): BattlePredictionUiModel {
    val formattedPower = if (power < 0) NO_EFFECT_VALUE else power.toString()
    val formattedAccuracy = if (accuracy < 0) NO_EFFECT_VALUE else String.format(format, accuracy)
    val formattedMultiplier = if (power < 0) NO_EFFECT_VALUE else String.format(format, multiplier)
    val formattedResult =
        if (calculatedResult < 0) NO_EFFECT_VALUE else String.format(format, calculatedResult)

    val color = selectedColorResource(multiplier)

    return BattlePredictionUiModel(
        power = formattedPower,
        accuracy = formattedAccuracy,
        multiplier = formattedMultiplier,
        calculatedResult = formattedResult,
        colorRes = color,
    )
}

private fun selectedColorResource(value: Double): Int =
    when {
        value < 1.0 -> R.color.poke_grey_60
        value in 1.0..2.9 -> R.color.poke_red_20
        value >= 3 -> R.color.poke_green_20
        else -> R.color.poke_white
    }
