package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.service.AbilityService

class RemoteAbilityDataSource(private val abilityService: AbilityService) {
    suspend fun abilities() = abilityService.abilities().data.map { it.toData() }
}
