package poke.rogue.helper.data.datasource

import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.exception.getOrThrow
import poke.rogue.helper.data.exception.onFailure
import poke.rogue.helper.data.model.Ability
import poke.rogue.helper.data.model.AbilityDetail
import poke.rogue.helper.data.model.toData
import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import poke.rogue.helper.remote.injector.ServiceModule
import poke.rogue.helper.remote.service.AbilityService

class RemoteAbilityDataSource(
    private val abilityService: AbilityService,
    private val logger: AnalyticsLogger,
) {
    suspend fun abilities(): List<Ability> =
        abilityService.abilities()
            .onFailure {
                logger.logError(throwable, "abilityService - abilities() 에서 발생")
            }
            .getOrThrow()
            .map(AbilityResponse::toData)

    suspend fun abilityDetail(id: String): AbilityDetail =
        abilityService.ability(id)
            .onFailure {
                logger.logError(throwable, "abilityService - ability($id) 에서 발생")
            }
            .getOrThrow()
            .toData()

    companion object {
        private var instance: RemoteAbilityDataSource? = null

        fun instance(): RemoteAbilityDataSource {
            return instance
                ?: RemoteAbilityDataSource(
                    ServiceModule.abilityService(),
                    analyticsLogger(),
                ).also { instance = it }
        }
    }
}
