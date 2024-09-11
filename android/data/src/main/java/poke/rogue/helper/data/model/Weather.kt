package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.battle.WeatherResponse

data class Weather(
    val id: String,
    val name: String,
    val description: String,
    val effects: List<String>,
)

fun WeatherResponse.toData(): Weather =
    Weather(
        id = id,
        name = name,
        description = description,
        effects = effects,
    )
