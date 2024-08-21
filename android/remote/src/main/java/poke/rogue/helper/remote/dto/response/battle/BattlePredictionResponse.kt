package poke.rogue.helper.remote.dto.response.battle

import kotlinx.serialization.Serializable

@Serializable
data class BattlePredictionResponse(
    val multiplier: Double,
    val power: Int,
    val accuracy: Double,
    val moveName: String,
    val moveDescription: String,
    val moveType: String,
    val moveCategory: String,
)
