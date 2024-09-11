package poke.rogue.helper.remote.dto.response.battle

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val id: String,
    val name: String,
    val description: String,
    val effects: List<String>,
)
