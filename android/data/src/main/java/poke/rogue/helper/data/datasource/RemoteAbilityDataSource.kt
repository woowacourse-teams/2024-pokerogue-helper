package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.Ability
import poke.rogue.helper.data.model.AbilityDetail
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.service.AbilityService

class RemoteAbilityDataSource(private val abilityService: AbilityService) {
    suspend fun abilities(): List<Ability> = abilityService.abilities().data.map { it.toData() }

    suspend fun abilityDetail(id: Long): AbilityDetail = abilityService.ability(id).data.toData()
}
