package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.BattleSkill
import poke.rogue.helper.data.model.Weather

interface BattleRepository {
    suspend fun weathers(): List<Weather>

    suspend fun availableSkills(dexNumber: Long): List<BattleSkill>
}
