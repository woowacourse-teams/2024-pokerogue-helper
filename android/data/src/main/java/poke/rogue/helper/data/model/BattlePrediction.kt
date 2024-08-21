package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.battle.BattlePredictionResponse

data class BattlePrediction(val power: Int, val accuracy: Double, val multiplier: Double, val calculatedResult: Double)

fun BattlePredictionResponse.toData(): BattlePrediction =
    BattlePrediction(power = power, accuracy = accuracy, multiplier = multiplier, calculatedResult = power * multiplier)
