package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.Weather

interface BattleRepository {
    suspend fun weathers(): List<Weather>
}
