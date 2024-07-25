package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.AbilityDetail

interface AbilityDetailRepository {
    suspend fun abilityDetail(id: Long): AbilityDetail
}
