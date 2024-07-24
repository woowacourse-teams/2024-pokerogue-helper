package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.Ability
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.dto.response.ability.AbilityDetailResponse
import poke.rogue.helper.remote.service.AbilityService

class RemoteAbilityDataSource(private val abilityApi: AbilityService) {
    suspend fun abilities(): List<Ability> {
        return abilityApi.abilities().data.map { it.toData() }
    }

    fun ability(id: Int): AbilityDetailResponse {
        TODO("Not yet implemented")
    }
}
