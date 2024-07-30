package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.Ability
import poke.rogue.helper.data.model.AbilityDetail

interface AbilityRepository {
    suspend fun abilities(): List<Ability>

    suspend fun abilities(query: String): List<Ability>

    suspend fun abilityDetail(id: Long): AbilityDetail
}
