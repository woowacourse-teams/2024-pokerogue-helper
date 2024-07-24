package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.RemoteAbilityDataSource
import poke.rogue.helper.data.model.Ability

class DefaultAbilityRepository(private val remoteAbilityDataSource: RemoteAbilityDataSource) :
    AbilityRepository {
    override suspend fun abilities(): List<Ability> {
        return remoteAbilityDataSource.abilities()
    }
}
