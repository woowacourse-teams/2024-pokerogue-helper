package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemoteAbilityDetailDataSource
import poke.rogue.helper.data.model.AbilityDetail

class DefaultAbilityDetailRepository(private val remoteAbilityDetailDataSource: RemoteAbilityDetailDataSource) :
    AbilityDetailRepository {
    override suspend fun abilityDetail(id: Long): AbilityDetail = remoteAbilityDetailDataSource.abilityDetail(id)
}
