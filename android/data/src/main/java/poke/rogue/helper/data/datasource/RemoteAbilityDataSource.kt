package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.Ability
import poke.rogue.helper.data.model.AbilityDetail
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.ServiceModule
import poke.rogue.helper.remote.service.AbilityService

class RemoteAbilityDataSource(private val abilityService: AbilityService) {
    suspend fun abilities(): List<Ability> =
        abilityService.abilities().data
            .map { it.toData() }
            .filter { it.description.contains("존재하지 않습니다").not() } // TODO: 서버가 처리하도록 변경

    suspend fun abilityDetail(id: Long): AbilityDetail = abilityService.ability(id).data.toData()

    companion object {
        private var instance: RemoteAbilityDataSource? = null

        fun instance(): RemoteAbilityDataSource {
            return instance
                ?: RemoteAbilityDataSource(ServiceModule.abilityService()).also { instance = it }
        }
    }
}
